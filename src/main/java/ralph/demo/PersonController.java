package ralph.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ralph on 6/18/16.
 */
@Controller
public class PersonController {

    @RequestMapping(path = "/person", method = RequestMethod.GET)
    String index(ModelMap modelMap) {
        return "edit-person";
    }

    @RequestMapping(path = "/person", method = RequestMethod.POST)
    void write() {

    }

}
