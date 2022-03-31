package code.backend.service.subService;

import java.text.MessageFormat;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import code.backend.helpers.utils.SubUtils;
import code.backend.persitence.entities.Account;
import code.backend.persitence.entities.VerificationToken;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String html, String from) throws MailException, MessagingException {
        String msg = html;
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("shegga10x@gmail.com");
        helper.setTo(to);
        helper.setText(msg);
        message.setContent(msg, "text/html");
        helper.setSubject(subject);
        javaMailSender.send(message);
    }

    public void sendVerificationEmail(Account account, String origin, VerificationToken verificationToken) {
        String message;
        if (!SubUtils.isNullOrBlank(origin)) {
            String verifyUrl = MessageFormat.format(
                    "{0}/account/verify-email?token={1}", origin,
                    verificationToken.getVerificationTokenContent());
            message = MessageFormat.format(
                    "<p>Please click the below link to verify your email address:</p><p><a href=\"\"{0}\"\">{0}</a></p>",
                    verifyUrl);
        } else {

            message = MessageFormat.format(
                    "<p>Please use the below token to verify your email address with the <code>/accounts/verify-email</code> api route:</p><p><code>{0}</code></p>",
                    account.getVerificationToken().getVerificationTokenContent());
        }
        try {
            sendMail(account.getEmail(), "Sign-up Verification API - Verify Email",
                    MessageFormat.format("<h4>Verify Email</h4><p>Thanks for registering!</p>{0}", message), null);
        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendAlreadyRegisteredEmail(String email, String origin) {
        String message;
        if (!SubUtils.isNullOrBlank(origin))
            message = MessageFormat.format(
                    "<p>If you don't know your password please visit the <a href=\"\"{0}/account/forgot-password\"\">forgot password</a> page.</p>",
                    origin);
        else
            message = "<p>If you don't know your password you can reset it via the <code>/accounts/forgot-password</code> api route.</p>";
        try {
            sendMail(email, "Sign-up Verification API - Email Already Registered", message, null);
        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendPasswordResetEmail(Account account, String origin) {
        String message;
        if (!SubUtils.isNullOrBlank(origin)) {
            String resetUrl = MessageFormat.format(
                    "{0}/account/reset-password?token={1}", origin, account.getResetToken().getResetTokenContent());
            message = MessageFormat.format(
                    "<p>Please click the below link to reset your password, the link will be valid for 1 day:</p><p><a href=\"\"{0}\"\">{0}</a></p>",
                    resetUrl);
        } else {
            message = MessageFormat.format(
                    "<p>Please use the below token to reset your password with the <code>/account/reset-password?token={0}</code> </p>",
                    account.getResetToken().getResetTokenContent());
        }
        try {
            sendMail(account.getEmail(), "Sign-up Verification API - Reset Password",
                    MessageFormat.format(
                            "<h4>Reset Password Email</h4>{0}", message),
                    null);
        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}