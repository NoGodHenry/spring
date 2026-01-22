package school.spring.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class AuthResponse {
    private boolean success;
    private String message;

    public static AuthResponse of(boolean success, String message) {
        return new AuthResponse(success, message);
    }

    public static AuthResponse of(boolean success) {
        return new AuthResponse(success, "");
    }
}
