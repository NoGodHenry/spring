package school.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(min = 2, max = 16)
    private String username;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="displayName", column=@Column(name="display_name")),
            @AttributeOverride(name="gender", column=@Column(name="gender"))
    })
    private UserDetail detail;

    @NotBlank
    @JsonIgnore
    @Length(min = 2, max = 120)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(max = 100)
    private List<Event> events = new ArrayList<>();

    public User() {
    }

    public User(final String username, final String password) {
        this.username = username;
        this.password = password;
        this.detail = new UserDetail();
        this.detail.setDisplayName(username);
    }
}
