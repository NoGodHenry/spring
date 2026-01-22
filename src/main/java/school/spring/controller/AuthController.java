package school.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.spring.entity.User;
import school.spring.payload.request.AuthRequest;
import school.spring.payload.response.AuthResponse;
import school.spring.repository.UserRepository;

import java.util.Optional;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated AuthRequest request,
                                   HttpServletRequest httpRequest) {

        // Authenticate using Spring Security
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication auth = authenticationManager.authenticate(token);

        // Store authentication in SecurityContext + session
        SecurityContextHolder.getContext().setAuthentication(auth);
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return ResponseEntity.ok(AuthResponse.of(true));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated AuthRequest request,
                                      HttpServletRequest httpRequest) {

        // Check if username exists
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(409).body(AuthResponse.of(false));
        }

        // Save user with encoded password
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(user);

        // Authenticate new user immediately
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication auth = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(auth);
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return ResponseEntity.ok(AuthResponse.of(true));
    }
}
