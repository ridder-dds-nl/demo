package ralph.demo.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ralph on 6/19/16.
 */
@Controller(value = "/")
public class HomeController {

    @RequestMapping
    public String index() {
        return "index";
    }
}
