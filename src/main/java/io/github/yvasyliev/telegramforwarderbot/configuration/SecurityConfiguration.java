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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@Import(AuthenticationConfiguration.class)
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        var authenticationManager = (ProviderManager) authConfig.getAuthenticationManager();
        var provider = (AbstractUserDetailsAuthenticationProvider) authenticationManager.getProviders().getFirst();
        provider.setHideUserNotFoundExceptions(false);
        return authenticationManager;
    }

    @Bean
    public UserDetailsService userDetailsService(TelegramProperties telegramProperties) {
        return new InMemoryUserDetailsManager(AuthUtils.createAdmin(telegramProperties.adminId()));
    }
}
