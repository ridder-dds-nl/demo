package ralph;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Our demo application.
 */
@SpringBootApplication
@EnableTransactionManagement
public class DemoApplication {

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


    /**
     * Starts up te application.
     *
     * @param args arguments are ignored.
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


}

