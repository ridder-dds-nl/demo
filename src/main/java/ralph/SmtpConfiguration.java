package ralph;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by ralph on 6/19/16.
 */
@Configuration
public class SmtpConfiguration {

    @Value("${application.smtp.server}")
    private String smtpServer;

    @Value("${application.smtp.port}")
    private Integer smtpPort;

    @Value("${application.smtp.user}")
    private String smtpUser;

    @Value("${application.smtp.password}")
    private String smtpPassword;

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpServer);
        mailSender.setPort(smtpPort);
        mailSender.setUsername(smtpUser);
        mailSender.setPassword(smtpPassword);
        mailSender.setJavaMailProperties(createJavaMailProperties());
        return mailSender;
    }

    private static Properties createJavaMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        return properties;
    }
}
