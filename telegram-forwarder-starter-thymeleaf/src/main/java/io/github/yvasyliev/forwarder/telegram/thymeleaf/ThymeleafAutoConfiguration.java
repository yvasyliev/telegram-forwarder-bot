package io.github.yvasyliev.forwarder.telegram.thymeleaf;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.convert.support.DefaultConversionService;
import org.thymeleaf.spring6.expression.ThymeleafEvaluationContext;

/**
 * Auto-configuration class for setting up Thymeleaf integration in the Telegram Forwarder application.
 * This configuration provides beans for customizing the Thymeleaf template context and integrating a Telegram-specific
 * template engine.
 */
@AutoConfiguration
@EnableAspectJAutoProxy
@ComponentScan
public class ThymeleafAutoConfiguration {
    /**
     * Creates a {@link TemplateContextCustomizer} bean that sets up the Thymeleaf evaluation context.
     *
     * @param applicationContext the Spring application context
     * @return a {@link TemplateContextCustomizer} that adds the Thymeleaf evaluation context to the template context
     */
    @Bean
    public TemplateContextCustomizer thymeleafEvaluationContextSetter(ApplicationContext applicationContext) {
        var evaluationContext = new ThymeleafEvaluationContext(applicationContext, new DefaultConversionService());

        return context -> context.setVariable(
                ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                evaluationContext
        );
    }
}
