package code.backend.helpers.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * ControlerUtils
 */
@Component
public class ControlerUtils {
    @Value("${bezkoder.app.jwtRefreshExpirationMs}")
    public int jwtRefreshExpirationMs;

    public void setTokenCookie(HttpServletResponse servletResponse, String token) {
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtRefreshExpirationMs);
        servletResponse.addCookie(cookie);

    }

    public String ipAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        if (request.getHeader("X-Forwarded-For") != null)
            return request.getHeader("X-Forwarded-For");
        else
            return request.getRemoteAddr();
    }

    public String getSingleFormCookie(String objectName) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (objectName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}