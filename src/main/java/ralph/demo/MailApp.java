package ralph.demo;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by ralph on 6/19/16.
 */
public class MailApp {

    public static void main() {

        MailSender mailSender = createMailSender();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Another one ur app!!");
        simpleMailMessage.setTo("ridder@dds.nl");
        simpleMailMessage.setText("Your password");
        mailSender.send(simpleMailMessage);

    }

    private static MailSender createMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(createJavaMailProperties());
        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername("ralph.portbase");
        mailSender.setPassword("280Aspen!");
//      mailSender.setPort(465);
        mailSender.setPort(587);
        return mailSender;
    }

    private static Properties createJavaMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        return properties;
    }

}
