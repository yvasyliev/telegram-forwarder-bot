package io.github.yvasyliev.telegramforwarder.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.thymeleaf.context.AbstractContext;

import java.util.List;

/**
 * An aspect that intercepts template processing in the TelegramTemplateEngine
 * to customize the template context using registered context customizers.
 */
@Aspect
@RequiredArgsConstructor
public class TelegramTemplateEngineInterceptor {
    private final List<TemplateContextCustomizer> contextCustomizers;

    /**
     * Intercepts the execution of template processing methods in the TelegramTemplateEngine.
     * It looks for an argument of type {@link AbstractContext} and applies all registered context customizers to it.
     *
     * @param joinPoint the join point representing the method execution
     */
    @Before("io.github.yvasyliev.telegramforwarder.thymeleaf.Pointcuts.processTemplate()")
    public void customizeContext(JoinPoint joinPoint) {
        for (var arg : joinPoint.getArgs()) {
            if (arg instanceof AbstractContext context) {
                contextCustomizers.forEach(contextCustomizer -> contextCustomizer.accept(context));
                return;
            }
        }
    }
}
