package code.backend.helpers.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import code.backend.persitence.entities.Account;
import code.backend.persitence.entities.RefreshToken;
import code.backend.persitence.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;
    @Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${bezkoder.app.jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public String generateJwtToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getEmail())
                .claim("email", account.getEmail())
                .claim("birthday", account.getLastExpires().toInstant().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public RefreshToken generateRefreshToken(String ipAddress, String idUser) {
        Date created = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(created);
        cal.add(Calendar.MILLISECOND, jwtRefreshExpirationMs);
        Date expires = cal.getTime();
        RefreshToken refreshToken = new RefreshToken(idUser, getRandomHexString(64), expires, created, ipAddress);
        boolean tokenIsUnique = !refreshTokenRepository.existsByToken(refreshToken.getToken());

        if (!tokenIsUnique)
            return generateRefreshToken(ipAddress, idUser);

        return refreshToken;
    }

    public String getRandomHexString(int numchars) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while (sb.length() < numchars) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }

}
