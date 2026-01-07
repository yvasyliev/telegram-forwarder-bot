package io.github.yvasyliev.telegramforwarder.bot.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for authentication-related operations. Provides methods to check user roles and create user details.
 */
@UtilityClass
public class AuthUtils {
    /**
     * A dummy password used for creating user details.
     */
    @SuppressWarnings({"checkstyle:VisibilityModifier", "checkstyle:MemberName"})
    public final String DUMMY_PASSWORD = "dummy_password";

    /**
     * The role identifier for admin users.
     */
    @SuppressWarnings({"checkstyle:VisibilityModifier", "checkstyle:MemberName"})
    public final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * Checks if the current user has admin privileges.
     *
     * @return {@code true} if the user is an admin, {@code false} otherwise.
     */
    public boolean isAdmin() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null
                && AuthorityUtils.authorityListToSet(authentication.getAuthorities()).contains(ROLE_ADMIN);
    }
}
