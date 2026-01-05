package io.github.yvasyliev.telegramforwarder.thymeleaf;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.convert.support.DefaultConversionService;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.expression.ThymeleafEvaluationContext;

import java.util.List;

/**
 * Auto-configuration class for setting up Thymeleaf integration in the Telegram Forwarder application.
 * This configuration provides beans for customizing the Thymeleaf template context and integrating a Telegram-specific
 * template engine.
 */
@AutoConfiguration
@EnableAspectJAutoProxy
public class ThymeleafAutoConfiguration {
    /**
     * Creates a {@link TemplateContextCustomizer} bean that sets up the Thymeleaf evaluation context.
     *
     * @param applicationContext the Spring application context
     * @return a {@link TemplateContextCustomizer} that adds the Thymeleaf evaluation context to the template context
     */
    @Bean
    @ConditionalOnMissingBean(name = "thymeleafEvaluationContextSetter")
    public TemplateContextCustomizer thymeleafEvaluationContextSetter(ApplicationContext applicationContext) {
        var evaluationContext = new ThymeleafEvaluationContext(applicationContext, new DefaultConversionService());

        return context -> context.setVariable(
                ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                evaluationContext
        );
    }

    /**
     * Creates a {@link TelegramTemplateProcessor} bean that processes Telegram templates using Thymeleaf.
     *
     * @param templateEngine the Thymeleaf template engine
     * @return a new instance of {@link TelegramTemplateProcessor}
     */
    @Bean
    @ConditionalOnMissingBean
    public TelegramTemplateProcessor telegramTemplateProcessor(ISpringTemplateEngine templateEngine) {
        return new TelegramTemplateProcessor(templateEngine);
    }

    /**
     * Creates a {@link TelegramTemplateProcessorInterceptor} bean that applies the provided context customizers
     * to the Thymeleaf template context.
     *
     * @param contextCustomizers a list of context customizers to apply
     * @return a new instance of {@link TelegramTemplateProcessorInterceptor}
     */
    @Bean
    @ConditionalOnMissingBean
    public TelegramTemplateProcessorInterceptor telegramTemplateProcessorInterceptor(
            List<TemplateContextCustomizer> contextCustomizers
    ) {
        return new TelegramTemplateProcessorInterceptor(contextCustomizers);
    }
}
