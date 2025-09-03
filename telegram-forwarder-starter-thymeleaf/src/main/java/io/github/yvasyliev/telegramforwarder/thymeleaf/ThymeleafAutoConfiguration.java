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

@AutoConfiguration
@EnableAspectJAutoProxy
public class ThymeleafAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(name = "thymeleafEvaluationContextSetter")
    public TemplateContextCustomizer thymeleafEvaluationContextSetter(ApplicationContext applicationContext) {
        var evaluationContext = new ThymeleafEvaluationContext(applicationContext, new DefaultConversionService());

        return context -> context.setVariable(
                ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                evaluationContext
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramTemplateEngine telegramTemplateEngine(ISpringTemplateEngine delegate) {
        return new TelegramTemplateEngine(delegate);
    }

    @Bean
    @ConditionalOnMissingBean
    public TelegramTemplateEngineInterceptor telegramTemplateEngineInterceptor(
            List<TemplateContextCustomizer> contextCustomizers
    ) {
        return new TelegramTemplateEngineInterceptor(contextCustomizers);
    }
}
