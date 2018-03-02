package pl.javastart.springsecurity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(){

        SecurityContextHolder.clearContext();

        return "<script>alert(\"Zostałeś wylogowany\"); window.location = \"/login\"</script>";
    }

}
