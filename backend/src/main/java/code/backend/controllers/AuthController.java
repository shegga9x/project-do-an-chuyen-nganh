package code.backend.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import code.backend.helpers.payload.request.AuthenticateRequest;
import code.backend.helpers.payload.request.ForgotPasswordRequest;
import code.backend.helpers.payload.request.RegisterRequest;
import code.backend.helpers.payload.request.ResetPasswordRequest;
import code.backend.helpers.payload.request.ValidateResetTokenRequest;
import code.backend.helpers.payload.request.VerifyEmailRequest;
import code.backend.helpers.payload.response.MessageResponse;
import code.backend.helpers.utils.ControlerUtils;
import code.backend.helpers.utils.SubUtils;
import code.backend.persitence.model.UserDetailCustom;
import code.backend.service.AccountService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/accounts")
public class AuthController {
    @Autowired
    ControlerUtils controlerUtils;
    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest model, HttpServletRequest request)
            throws IllegalAccessException, InvocationTargetException {
        accountService.register(model, request.getHeader("origin"));
        return ResponseEntity.ok(
                new MessageResponse("Registration successful, please check your email for verification instructions"));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@Valid @RequestBody VerifyEmailRequest model) {
        accountService.verifyEmail(model.getToken());
        return ResponseEntity.ok(
                new MessageResponse("Verification successful, you can now login"));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticateRequest model,
            HttpServletResponse servletResponse)
            throws IllegalAccessException, InvocationTargetException {
        var response = accountService.authenticate(model, controlerUtils.ipAddress());
        if (response == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Wrong password or Not Active yet !!!"));
        } else {
            controlerUtils.setTokenCookie(servletResponse, response.getRefreshToken());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletResponse servletResponse)
            throws IllegalAccessException, InvocationTargetException {
        String refreshToken = controlerUtils.getSingleFormCookie("refreshToken");
        var response = accountService.refreshToken(refreshToken, controlerUtils.ipAddress());
        try {
            controlerUtils.setTokenCookie(servletResponse, response.getRefreshToken());
        } catch (Exception e) {
            controlerUtils.setTokenCookie(servletResponse, "");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/revoke-token")
    public ResponseEntity<?> revokeToken() {
        String token = controlerUtils.getSingleFormCookie("refreshToken");
        if (SubUtils.isNullOrBlank(token))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Token is required"));
        UserDetailCustom currentAccount = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        List<String> roles = currentAccount.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList()).stream().filter(x -> x.equals("Admin")).collect(Collectors.toList());
        if (!currentAccount.OwnsToken(token) && roles.size() > 0)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Unauthorized"));
        accountService.revokeToken(token, controlerUtils.ipAddress());
        return ResponseEntity.ok(new MessageResponse("Token revoked"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest model) {
        accountService.forgotPassword(model, controlerUtils.getSingleFormCookie("origin"));
        return ResponseEntity.ok(
                new MessageResponse("Please check your email for password reset instructions"));
    }

    @PostMapping("/validate-reset-token")
    public ResponseEntity<?> validateResetToken(@Valid @RequestBody ValidateResetTokenRequest model) {
        accountService.validateResetToken(model);
        return ResponseEntity.ok(
                new MessageResponse("Token is valid"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest model) {
        accountService.resetPassword(model);
        return ResponseEntity.ok(
                new MessageResponse("Password reset successful, you can now login"));
    }

}