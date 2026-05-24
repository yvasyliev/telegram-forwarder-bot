package io.github.yvasyliev.forwarder.telegram.core.configuration;

import io.github.yvasyliev.forwarder.telegram.core.util.InputFileDTOConverter;
import io.github.yvasyliev.forwarder.telegram.core.util.InputMediaPhotoDTOConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the Telegram Forwarder Core utilities.
 */
@Configuration
public class TelegramForwarderCoreUtilConfiguration {
    /**
     * Creates a bean of type {@link InputFileDTOConverter} if one is not already present in the application context.
     *
     * @return a new instance of {@link InputFileDTOConverter}
     */
    @Bean
    @ConditionalOnMissingBean
    public InputFileDTOConverter inputFileDTOConverter() {
        return new InputFileDTOConverter();
    }

    /**
     * Creates a bean of type {@link InputMediaPhotoDTOConverter} if one is not already present in the application
     * context.
     *
     * @return a new instance of {@link InputMediaPhotoDTOConverter}
     */
    @Bean
    @ConditionalOnMissingBean
    public InputMediaPhotoDTOConverter inputMediaPhotoDTOConverter() {
        return new InputMediaPhotoDTOConverter();
    }
}
