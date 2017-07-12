package melfood.controller.common;

import melfood.framework.system.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Steven on 11/7/17.
 */
@Controller
@RequestMapping("/welcome")
public class WelcometoController extends BaseController {

    @RequestMapping("/you")
    public ModelAndView main() throws Exception {
        ModelAndView mav = new ModelAndView("tiles/common/welcome/you");
        return mav;
    }
}
