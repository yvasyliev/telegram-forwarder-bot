package io.github.yvasyliev.forwarder.telegram.bot.security.core;

import io.github.yvasyliev.forwarder.telegram.bot.util.AuthUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Represents a Telegram user in the security context.
 */
public final class TelegramUser extends User {
    private static final String DUMMY_PASSWORD = "{noop}" + AuthUtils.DUMMY_PASSWORD;
    private static final Collection<? extends GrantedAuthority> ADMIN_AUTHORITIES
            = AuthorityUtils.createAuthorityList(AuthUtils.ROLE_ADMIN);
    private static final Collection<? extends GrantedAuthority> USER_AUTHORITIES
            = AuthorityUtils.createAuthorityList("ROLE_USER");

    private TelegramUser(Long userId, Collection<? extends GrantedAuthority> authorities) {
        super(userId.toString(), DUMMY_PASSWORD, authorities);
    }

    /**
     * Creates a {@link TelegramUser} with admin authorities.
     *
     * @param userId the Telegram user ID
     * @return a {@link TelegramUser} instance with admin authorities
     */
    public static TelegramUser createAdmin(Long userId) {
        return createUser(userId, ADMIN_AUTHORITIES);
    }

    /**
     * Creates a {@link TelegramUser} with user authorities.
     *
     * @param userId the Telegram user ID
     * @return a {@link TelegramUser} instance with user authorities
     */
    public static TelegramUser createUser(Long userId) {
        return createUser(userId, USER_AUTHORITIES);
    }

    private static TelegramUser createUser(Long userId, Collection<? extends GrantedAuthority> authorities) {
        return new TelegramUser(userId, authorities);
    }
}
