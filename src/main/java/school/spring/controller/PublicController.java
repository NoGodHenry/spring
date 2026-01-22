package school.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.spring.payload.request.AuthRequest;
import school.spring.principal.AuthUserDetail;
import school.spring.repository.UserRepository;

@Controller
public class PublicController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index(
            Model model,
            @AuthenticationPrincipal AuthUserDetail principal
    ) {
        if (principal != null) {
            model.addAttribute("user",
                    userRepository.findById(principal.getId())
                            .orElseThrow()
                            .getDetail()
            );
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(final Model model, final HttpSession session) {
        //String username = (String) session.getAttribute("USER_ID");

//        model.addAttribute("username", username);

        return "login";
    }

    @GetMapping("/register")
    public String register(final Model model, final HttpSession session) {
        //String username = (String) session.getAttribute("USER_ID");

//        model.addAttribute("username", username);

        return "register";
    }
}
