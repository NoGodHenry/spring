package school.spring.principal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.spring.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class AuthUserDetail implements UserDetails {

    @Getter
    private final Long id;
    private final String username;
    private final String password;

    public static AuthUserDetail from(User user) {
        return new AuthUserDetail(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
