package school.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "events", indexes = {
        @Index(name = "idx_event_start", columnList = "startTime"),
        @Index(name = "idx_event_end", columnList = "endTime")
})
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Event() {}

    public Event(final User user, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}