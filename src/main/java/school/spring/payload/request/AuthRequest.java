package school.spring.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@ToString
@Getter
public final class AuthRequest {

    @NotBlank
    @Length(min = 2, max = 16)
    private String username;

    @NotBlank
    @Length(min=2, max = 120)
    private String password;
}
