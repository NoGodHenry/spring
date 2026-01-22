package school.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.spring.entity.Event;
import school.spring.entity.User;
import school.spring.payload.request.EventRequest;
import school.spring.payload.response.EventsResponse;
import school.spring.principal.AuthUserDetail;
import school.spring.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/get")
    public ResponseEntity<?> getEvents(final @AuthenticationPrincipal AuthUserDetail detail) {
        final var user = userRepository.findById(detail.getId())
                .orElseThrow();

        return ResponseEntity.ok(new EventsResponse(user.getEvents()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEvent(final @Validated @RequestBody EventRequest request, final @AuthenticationPrincipal AuthUserDetail detail) {
        final var user = userRepository.findById(detail.getId())
                .orElseThrow();

        user.getEvents().add(new Event(
                user,
                request.getTitle(),
                request.getDescription(),
                request.getStartTime(),
                request.getEndTime()
        ));

        userRepository.save(user);

        return ResponseEntity.ok(new EventsResponse(user.getEvents()));
    }
}
