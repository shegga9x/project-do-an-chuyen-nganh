package code.backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import code.backend.helpers.advice.CustomException;
import code.backend.helpers.payload.request.AuthenticateRequest;
import code.backend.helpers.payload.request.ForgotPasswordRequest;
import code.backend.helpers.payload.request.RegisterRequest;
import code.backend.helpers.payload.request.ResetPasswordRequest;
import code.backend.helpers.payload.request.ValidateResetTokenRequest;
import code.backend.helpers.payload.response.AccountResponse;
import code.backend.helpers.payload.response.AuthenticateResponse;
import code.backend.helpers.utils.JwtUtils;
import code.backend.helpers.utils.SubUtils;
import code.backend.helpers.utils.TokenUtils;
import code.backend.persitence.entities.Account;
import code.backend.persitence.entities.AccountDetail;
import code.backend.persitence.entities.RefreshToken;
import code.backend.persitence.entities.ResetToken;
import code.backend.persitence.entities.Role;
import code.backend.persitence.entities.VerificationToken;
import code.backend.persitence.enumModel.RoleEnum;
import code.backend.persitence.repository.AccountRepository;
import code.backend.persitence.repository.RefreshTokenRepository;
import code.backend.persitence.repository.ResetTokenRepository;
import code.backend.persitence.repository.VerificationTokenRepository;
import code.backend.service.subService.EmailService;

@Service
@SuppressWarnings("unchecked")
public class AccountService {
    @Value("${bezkoder.app.jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    ResetTokenRepository resetTokenRepository;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    EmailService emailService;
    @Autowired
    TokenUtils tokenUtils;

    public void register(RegisterRequest model, String origin) {
        try {
            accountRepository.findByEmail(model.getEmail()).get();
            emailService.sendAlreadyRegisteredEmail(model.getEmail(), origin);
            throw new CustomException("This Email already be taken !!!");
        } catch (Exception e) {
        }
        Account account = new Account();
        account.setIdAccount(UUID.randomUUID().toString());
        AccountDetail accountDetail = new AccountDetail(account.getIdAccount());
        accountDetail = (AccountDetail) SubUtils.mapperObject(model, accountDetail);
        account.setAccountDetail(accountDetail);
        account = (Account) SubUtils.mapperObject(model, account);
        // first registered account is an admin
        account.setCreated(new Date());
        account.setPasswordHash(encoder.encode(model.getPassword()));
        account.setAcceptTerms(true);
        account.setLastExpires(new Date());
        boolean isFirstAccount = accountRepository.findAll().size() == 0;
        RoleEnum roleEnum = (isFirstAccount ? RoleEnum.Admin : RoleEnum.Student);
        List<Role> roles = new ArrayList<>(List.of(new Role(roleEnum)));
        account.setListOfRole(roles);
        VerificationToken verificationToken = new VerificationToken(account.getIdAccount(),
                tokenUtils.generateVerificationToken());
        account.setVerificationToken(verificationToken);
        account.setResetToken(new ResetToken(account.getIdAccount()));
        account.setAccountDetail(accountDetail);
        account = accountRepository.save(account);
        emailService.sendVerificationEmail(account, origin, verificationToken);
    }

    public void verifyEmail(String token) {
        VerificationToken verificationToken = null;
        try {
            verificationToken = verificationTokenRepository.findByVerificationTokenContent(token).get();
        } catch (Exception e) {
            System.out.println(e);
            throw new CustomException("Can't find token !!!");
        }
        verificationToken.setVerified(new Date());
        verificationToken.setVerificationTokenContent(null);
        verificationTokenRepository.save(verificationToken);
    }

    public AuthenticateResponse authenticate(AuthenticateRequest model, String ipAddress) {
        Account account = accountRepository.findByEmail(model.email).get();
        if (account == null || account.getVerificationToken().getVerified() == null
                || !encoder.matches(model.password, account.getPasswordHash())) {
            throw new CustomException("Wrong password or Not Active yet !!!");
        } else {
            account.setLastExpires(new Date());
            // delete OldRefreshTokens
            RefreshToken refreshToken = jwtUtils.generateRefreshToken(ipAddress, account.getIdAccount());
            refreshTokenRepository.save(refreshToken);
            tokenUtils.removeOldRefreshTokens(account);

            List<String> roles = new ArrayList<>();
            for (Role role : account.getListOfRole()) {
                roles.add(role.getRoleName().toString());
            }
            // init respone
            String jwtToken = jwtUtils.generateJwtToken(account);
            AuthenticateResponse response = new AuthenticateResponse();
            response.setRole(roles);
            response.jwtToken = jwtToken;
            response.refreshToken = refreshToken.getToken();
            response = (AuthenticateResponse) SubUtils.mapperObject(account, response);
            return response;
        }
    }

    public AuthenticateResponse refreshToken(String token, String ipAddress) {
        RefreshToken refreshToken = null;
        try {
            refreshToken = refreshTokenRepository.findByToken(token).get();
        } catch (Exception e) {
            throw new CustomException("Can't find token !!!");
        }

        Account account = refreshToken.getAccount();
        if (refreshToken.IsRevoked() && refreshToken != null) {
            refreshToken = tokenUtils.revokeDescendantRefreshTokens(refreshToken, account, ipAddress,
                    "Attempted reuse of revoked ancestor token: " + token);
            refreshTokenRepository.save(refreshToken);
        }
        if (!refreshToken.IsActive()) {
            throw new CustomException("Token is UnActive !!!");

        }
        RefreshToken newRefreshToken = tokenUtils.rotateRefreshToken(refreshToken, ipAddress);
        refreshToken = tokenUtils.revokeRefreshToken(refreshToken, ipAddress, "Replaced by new token",
                newRefreshToken.getToken());
        tokenUtils.removeOldRefreshTokens(account);

        account.addToListOfRefreshToken(newRefreshToken);
        account.addToListOfRefreshToken(refreshToken);
        account.setLastExpires(new Date());
        String jwtToken = jwtUtils.generateJwtToken(account);
        List<String> roles = new ArrayList<>();
        for (Role role : account.getListOfRole()) {
            roles.add(role.getRoleName().toString());
        }
        AuthenticateResponse response = new AuthenticateResponse();
        response.role = roles;
        response.jwtToken = jwtToken;
        response.refreshToken = newRefreshToken.getToken();
        response = (AuthenticateResponse) SubUtils.mapperObject(account, response);
        accountRepository.save(account);
        return response;
    }

    public void revokeToken(String token, String ipAddress) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).get();
        if (!refreshToken.IsActive()) {
            throw new CustomException("Token is UnActive");
        }
        // revoke token and save
        refreshToken = tokenUtils.revokeRefreshToken(refreshToken, ipAddress, "Revoked without replacement", null);
        refreshTokenRepository.save(refreshToken);

    }

    public void forgotPassword(ForgotPasswordRequest model, String origin) {
        Account account = null;
        // always return ok response to prevent email enumeration
        try {
            account = accountRepository.findByEmail(model.getEmail()).get();
        } catch (Exception e) {
            throw new CustomException("Can't find Email !!!");
        }
        ResetToken resetToken = account.getResetToken();
        // create reset token that expires after 1 day
        resetToken.setResetTokenContent(tokenUtils.generateResetToken());
        resetToken.setResetTokenExpires(new Date(new Date().getTime() + jwtRefreshExpirationMs));
        resetTokenRepository.save(resetToken);
        // send email
        emailService.sendPasswordResetEmail(account, origin);
    }

    public void validateResetToken(ValidateResetTokenRequest model) {
        tokenUtils.getAccountByResetToken(model.getToken());
    }

    public void resetPassword(ResetPasswordRequest model) {
        Account account = tokenUtils.getAccountByResetToken(model.getToken());
        account.setResetToken(new ResetToken(account.getIdAccount(), new Date(), null, null));
        account.setPasswordHash(encoder.encode(model.getPassword()));
        accountRepository.save(account);
    }

    public List<AccountResponse> getAll() {
        List<AccountResponse> accountResponses = (List<AccountResponse>) SubUtils.mapperList(
                accountRepository.findAll(),
                new AccountResponse());
        return accountResponses;
    }
}