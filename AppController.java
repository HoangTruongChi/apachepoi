package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("name","Hoang Duc Truong");
        return "index";
    }
    @RequestMapping("/test")
    public String test(Model model){
        model.addAttribute("test","đây là test");
        return "index";
    }
}
