package ma.ac.emi.ginfo.restfull.services;

import lombok.AllArgsConstructor;
import ma.ac.emi.ginfo.restfull.entities.Contact;
import ma.ac.emi.ginfo.restfull.entities.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
@AllArgsConstructor
public class MailService {

    private MailConfig mc = new MailConfig();
    private JavaMailSender mailSender;
    @Autowired
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(String recipient, String subject, String template, Map<String, Object> templateModel) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom("no-reply@bikenme.emi.ac.ma");
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContentBuilder.build(template, templateModel ),true);
            mailSender.send(mimeMessage);
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Exception occurred when sending mail to " + recipient);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendVerificationMail(String recipient, String message, String subject, String user) {
        sendMail(recipient, subject,"mailTemplate.html",Map.of("message",message,"username",user));
    }

    @Async
    public void sendNotificationMail(String recipient, String subject, Notification notification) {
        sendMail(recipient, subject,"notificationTemplate.html",Map.of("notification",notification));
    }

    @Async
    public void sendEmail(Contact contact) {
        sendMail("moharbani01@gmail.com", contact.getSubject(), "messageTemplate.html",Map.of("contact",contact,"url",mc.url));
    }

}
