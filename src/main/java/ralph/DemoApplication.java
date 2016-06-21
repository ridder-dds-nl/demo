package ralph;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

/**
 * Our demo application.
 */
@SpringBootApplication
@EnableTransactionManagement
public class DemoApplication {

    @Bean
    public VelocityEngine velocityEngine() throws IOException {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        return velocityEngine;
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