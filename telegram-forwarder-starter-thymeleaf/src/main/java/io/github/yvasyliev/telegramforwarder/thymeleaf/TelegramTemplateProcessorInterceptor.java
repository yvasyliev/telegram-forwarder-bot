package io.github.yvasyliev.telegramforwarder.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.thymeleaf.context.AbstractContext;

import java.util.List;

/**
 * An AspectJ interceptor that customizes the Thymeleaf template context before processing templates.
 * It applies a list of {@link TemplateContextCustomizer} instances to modify the context as needed.
 */
@Aspect
@RequiredArgsConstructor
public class TelegramTemplateProcessorInterceptor {
    private final List<TemplateContextCustomizer> contextCustomizers;

    /**
     * Intercepts the template processing pointcut and customizes the Thymeleaf context.
     *
     * @param templateContext the template context containing the Thymeleaf context to be customized
     */
    @Before("io.github.yvasyliev.telegramforwarder.thymeleaf.Pointcuts.processTemplate() && args(templateContext)")
    public void customizeContext(TemplateContext templateContext) {
        if (templateContext.context() instanceof AbstractContext context) {
            contextCustomizers.forEach(contextCustomizer -> contextCustomizer.customize(context));
        }
    }
}
