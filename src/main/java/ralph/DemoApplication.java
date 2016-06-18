package ralph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Our demo application.
 */
@SpringBootApplication
@EnableTransactionManagement
public class DemoApplication {


    /**
     * Starts up te application.
     *
     * @param args arguments are ignored.
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


}

