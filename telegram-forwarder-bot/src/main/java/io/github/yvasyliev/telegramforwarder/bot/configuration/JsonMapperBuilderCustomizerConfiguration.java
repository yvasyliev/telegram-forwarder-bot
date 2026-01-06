package io.github.yvasyliev.telegramforwarder.bot.configuration;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.support.JacksonHandlerInstantiator;

/**
 * Configuration for {@link JsonMapperBuilderCustomizer} to integrate Spring-managed beans with Jackson.
 */
@Configuration
public class JsonMapperBuilderCustomizerConfiguration {
    /**
     * Customizes the Jackson JSON mapper builder to use a Spring-aware handler instantiator.
     *
     * @param applicationContext the Spring application context
     * @return a customizer for the JSON mapper builder
     */
    @Bean
    public JsonMapperBuilderCustomizer jacksonBuilderCustomizer(ApplicationContext applicationContext) {
        var handlerInstantiator = new JacksonHandlerInstantiator(applicationContext.getAutowireCapableBeanFactory());

        return builder -> builder.handlerInstantiator(handlerInstantiator);
    }
}
