package io.github.yvasyliev.telegramforwarder.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.thymeleaf.context.AbstractContext;

import java.util.List;

@Aspect
@RequiredArgsConstructor
public class TelegramTemplateEngineInterceptor {
    private final List<TemplateContextCustomizer> contextCustomizers;

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
