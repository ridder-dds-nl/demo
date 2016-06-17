package ralph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Our demo application.
 */
@SpringBootApplication
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

