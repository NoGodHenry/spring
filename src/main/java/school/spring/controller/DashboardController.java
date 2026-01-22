package school.spring.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import school.spring.entity.Event;
import school.spring.principal.AuthUserDetail;
import school.spring.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/dashboard")
    public String dashboard(final Model model, final @AuthenticationPrincipal AuthUserDetail detail) {
        final var user = userRepository.findById(detail.getId())
                .orElseThrow();

        model.addAttribute("events", user.getEvents());

        model.addAttribute("user", user.getDetail());

        return "dashboard";
    }
}
