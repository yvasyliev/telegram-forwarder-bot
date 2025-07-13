package io.github.yvasyliev.telegramforwarderbot.configuration;

import io.github.yvasyliev.telegramforwarderbot.util.AuthUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Security configuration for the Telegram Forwarder Bot.
 * This class sets up the authentication manager and user details service
 * for the application, allowing for secure access to methods and resources.
 */
@Configuration
@Import(AuthenticationConfiguration.class)
@EnableMethodSecurity
public class SecurityConfiguration {
    /**
     * Configures the authentication manager to allow for custom user details handling.
     * This method sets the {@code hideUserNotFoundExceptions} property to false,
     * enabling more informative error messages during authentication failures.
     *
     * @param authConfig the authentication configuration
     * @return the configured authentication manager
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        var authenticationManager = (ProviderManager) authConfig.getAuthenticationManager();
        var provider = (AbstractUserDetailsAuthenticationProvider) authenticationManager.getProviders().getFirst();
        provider.setHideUserNotFoundExceptions(false);
        return authenticationManager;
    }

    /**
     * Provides an in-memory user details service with an admin user.
     * This method creates a user details manager that contains a single admin user
     * with the ID specified in the Telegram properties.
     *
     * @param telegramProperties the Telegram properties containing the admin ID
     * @return the user details service with the admin user
     */
    @Bean
    public UserDetailsService userDetailsService(TelegramProperties telegramProperties) {
        return new InMemoryUserDetailsManager(AuthUtils.createAdmin(telegramProperties.adminId()));
    }
}
