package ralph.demo.account;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by ralph on 6/19/16.
 */
@Component
public class NewPasswordEmailMessageCreator {

    private final VelocityEngine velocityEngine;

    private final String subject = "Here is your new password.";

    @Autowired
    public NewPasswordEmailMessageCreator(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public String create() {
        Writer stringWriter = new StringWriter();
        VelocityContext context = new VelocityContext();
        context.put("password", "hidden");
        velocityEngine.mergeTemplate("/account/new-password.email.txt", "UTF-8", context, stringWriter);
        return stringWriter.toString();
    }

    public String getSubject() {
        return subject;
    }
}
