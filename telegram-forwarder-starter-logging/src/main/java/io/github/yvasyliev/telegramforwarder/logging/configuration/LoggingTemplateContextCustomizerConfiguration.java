package io.github.yvasyliev.telegramforwarder.logging.configuration;

import io.github.yvasyliev.telegramforwarder.thymeleaf.TemplateContextCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for logging template context customizers.
 */
@Configuration
public class LoggingTemplateContextCustomizerConfiguration {
    /**
     * Creates a {@link TemplateContextCustomizer} that sets the Telegram bot appender properties in the template
     * context.
     *
     * @param botAppenderProperties the Telegram bot appender properties
     * @return the template context customizer
     */
    @Bean
    @ConditionalOnMissingBean(name = "telegramBotAppenderPropertiesSetter")
    public TemplateContextCustomizer telegramBotAppenderPropertiesSetter(
            TelegramBotAppenderProperties botAppenderProperties
    ) {
        return context -> context.setVariable("botAppenderProperties", botAppenderProperties);
    }
}
