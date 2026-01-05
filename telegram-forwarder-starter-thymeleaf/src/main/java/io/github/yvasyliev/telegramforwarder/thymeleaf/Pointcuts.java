package io.github.yvasyliev.telegramforwarder.thymeleaf;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Pointcuts for aspect-oriented programming (AOP) in the Thymeleaf integration module.
 */
public class Pointcuts {
    /**
     * Pointcut that matches the execution of the process method in {@link TelegramTemplateProcessor}.
     */
    @Pointcut("execution(String io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateProcessor.process("
            + "io.github.yvasyliev.telegramforwarder.thymeleaf.TemplateContext))")
    public void processTemplate() {}
}
