package io.github.yvasyliev.telegramforwarderbot.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@UtilityClass
public class AuthUtils {
    public final String DEFAULT_PASSWORD = "{noop}";
    private final String ROLE_ADMIN = "ROLE_ADMIN";
    private final Collection<? extends GrantedAuthority> ADMIN_AUTHORITIES
            = AuthorityUtils.createAuthorityList(ROLE_ADMIN);
    private final Collection<? extends GrantedAuthority> USER_AUTHORITIES
            = AuthorityUtils.createAuthorityList("ROLE_USER");

    public boolean isAdmin() {
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return AuthorityUtils.authorityListToSet(authorities).contains(ROLE_ADMIN);
    }

    public UserDetails createAdmin(Long adminId) {
        return createUser(adminId, ADMIN_AUTHORITIES);
    }

    public UserDetails createUser(Long userId) {
        return createUser(userId, USER_AUTHORITIES);
    }

    public UserDetails createUser(Long userId, Collection<? extends GrantedAuthority> authorities) {
        return new User(userId.toString(), DEFAULT_PASSWORD, authorities);
    }
}
