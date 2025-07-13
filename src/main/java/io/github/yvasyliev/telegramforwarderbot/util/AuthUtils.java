package io.github.yvasyliev.telegramforwarderbot.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Utility class for authentication-related operations.
 * Provides methods to check user roles and create user details.
 */
@UtilityClass
public class AuthUtils {
    /**
     * Default password for users. Always {@code {noop}} for empty password without encoding.
     */
    @SuppressWarnings({"checkstyle:VisibilityModifier", "checkstyle:MemberName"})
    public final String DEFAULT_PASSWORD = "{noop}";

    @SuppressWarnings("checkstyle:MemberName")
    private final String ROLE_ADMIN = "ROLE_ADMIN";

    @SuppressWarnings("checkstyle:MemberName")
    private final Collection<? extends GrantedAuthority> ADMIN_AUTHORITIES
            = AuthorityUtils.createAuthorityList(ROLE_ADMIN);

    @SuppressWarnings("checkstyle:MemberName")
    private final Collection<? extends GrantedAuthority> USER_AUTHORITIES
            = AuthorityUtils.createAuthorityList("ROLE_USER");

    /**
     * Checks if the current user has admin privileges.
     *
     * @return {@code true} if the user is an admin, {@code false} otherwise.
     */
    public boolean isAdmin() {
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return AuthorityUtils.authorityListToSet(authorities).contains(ROLE_ADMIN);
    }

    /**
     * Creates a {@link UserDetails} object for an admin user with the specified ID.
     *
     * @param adminId the ID of the admin user
     * @return a {@link UserDetails} object representing the admin user
     */
    public UserDetails createAdmin(Long adminId) {
        return createUser(adminId, ADMIN_AUTHORITIES);
    }

    /**
     * Creates a {@link UserDetails} object for a regular user with the specified ID.
     *
     * @param userId the ID of the user
     * @return a {@link UserDetails} object representing the user
     */
    public UserDetails createUser(Long userId) {
        return createUser(userId, USER_AUTHORITIES);
    }

    /**
     * Creates a {@link UserDetails} object with the specified user ID and authorities.
     *
     * @param userId      the ID of the user
     * @param authorities the authorities granted to the user
     * @return a {@link UserDetails} object representing the user
     */
    public UserDetails createUser(Long userId, Collection<? extends GrantedAuthority> authorities) {
        return new User(userId.toString(), DEFAULT_PASSWORD, authorities);
    }
}
