package pl.javastart.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;



    @GetMapping("/dodaj")
    public String newUser(Model model){

        User user = new User();

        model.addAttribute("user", user);

        return "add_user";
    }

    @PostMapping("/dodaj")
    @ResponseBody
    public String addUser(User user){

        if (userRepository.getUserByUsername(user.getUsername()) != null){
            return "<script>alert(\"Taki użytkownik już istnieje!\"); window.location = \"/dodaj\"</script>";
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String hashedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(hashedPassword);

        user.setEnabled(true);

        userRepository.save(user);

        UserRole userRole = new UserRole();

        userRole.setUsername(user.getUsername());
        userRole.setRole("ROLE_USER");

        userRoleRepository.save(userRole);

        String alert = "<script>alert(\"Użytkownik został dodany\"); window.location = \"/\"</script>";

        return alert;
    }

    @GetMapping("/profil")
    public String editUser(Model model, Principal principal){

        User user = userRepository.getUserByUsername(principal.getName());

        model.addAttribute("user", user);

        return "edit_user";
    }

    @PostMapping("/profil")
    @ResponseBody
    public String updateUser(Model model, User user){

        if (userRepository.getUserByUsername(user.getUsername()) != null){
            return "<script>alert(\"Taki użytkownik już istnieje!\"); window.location = \"/profil\"</script>";
        }

        User newUser = userRepository.findOne(user.getId());

        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPhone(user.getPhone());
        newUser.setUsername(user.getUsername());

        userRepository.save(newUser);

        UserRole userRole = userRoleRepository.findOne(user.getId());

        userRole.setUsername(user.getUsername());
        //userRole.setRole("ROLE_USER");

        userRoleRepository.save(userRole);

        return "Updated";
    }

    @GetMapping("/haslo")
    public String newPassword(Model model, Principal principal){

        User user  = userRepository.getUserByUsername(principal.getName());

        model.addAttribute("user", user);

        return "password";
    }

    @PostMapping("/haslo")
    @ResponseBody
    public String passwordUpdate(User user, Principal principal){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String hashedPassword = passwordEncoder.encode(user.getPassword());

        User newUser = userRepository.getUserByUsername(principal.getName());

        newUser.setPassword(hashedPassword);

        userRepository.save(newUser);

        return "<script>alert(\"Hasło zmienione pomyślnie\"); window.location = \"/profil\"</script>";
    }

    @GetMapping("/usun")
    @ResponseBody
    public String deleteUser(Principal principal){

        userRepository.delete(userRepository.getUserByUsername(principal.getName()));

        userRoleRepository.delete(userRoleRepository.getUserRoleByUsername(principal.getName()));

        SecurityContextHolder.clearContext();

        return "<script>alert(\"Konto usunięte\"); window.location = \"/\"</script>";
    }

}
