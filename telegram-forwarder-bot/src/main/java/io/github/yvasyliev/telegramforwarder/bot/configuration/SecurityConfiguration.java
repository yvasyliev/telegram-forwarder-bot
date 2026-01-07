package io.github.yvasyliev.telegramforwarder.bot.configuration;

import io.github.yvasyliev.telegramforwarder.bot.security.authentication.TelegramAuthenticationManager;
import io.github.yvasyliev.telegramforwarder.bot.security.core.TelegramUser;
import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.util.CollectionUtils;

/**
 * Security configuration for the Telegram Forwarder Bot. This class sets up the authentication manager and user details
 * service for the application, allowing for secure access to methods and resources.
 */
@Configuration
@EnableGlobalAuthentication
@EnableMethodSecurity
public class SecurityConfiguration {
    /**
     * Configures the authentication manager to allow for custom user details handling. This method sets the
     * {@code hideUserNotFoundExceptions} property to {@code false}, enabling more informative error messages during
     * authentication failures.
     *
     * @param authConfig the authentication configuration
     * @return the configured authentication manager
     */
    @Bean
    public TelegramAuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) {
        var authenticationManager = authConfig.getAuthenticationManager();

        if (authenticationManager instanceof ProviderManager providerManager) {
            var authenticationProvider = CollectionUtils.findValueOfType(
                    providerManager.getProviders(),
                    AbstractUserDetailsAuthenticationProvider.class
            );

            if (authenticationProvider != null) {
                authenticationProvider.setHideUserNotFoundExceptions(false);
            }
        }

        return new TelegramAuthenticationManager(authenticationManager);
    }

    /**
     * Provides an in-memory user details service with an admin user. This method creates a user details manager that
     * contains a single admin user with the ID specified in the Telegram properties.
     *
     * @param adminProperties the Telegram admin properties
     * @return the user details service with the admin user
     */
    @Bean
    public UserDetailsService userDetailsService(TelegramAdminProperties adminProperties) {
        return new InMemoryUserDetailsManager(TelegramUser.createAdmin(adminProperties.id()));
    }
}
