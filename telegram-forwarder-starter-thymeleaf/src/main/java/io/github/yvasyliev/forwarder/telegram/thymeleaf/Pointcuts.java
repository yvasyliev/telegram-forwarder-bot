package io.github.yvasyliev.forwarder.telegram.thymeleaf;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Pointcuts for aspect-oriented programming (AOP) in the Thymeleaf integration module.
 */
public class Pointcuts {
    /**
     * Pointcut that matches the execution of the process method in {@link TelegramTemplateProcessor}.
     */
    @Pointcut("execution(String io.github.yvasyliev.forwarder.telegram.thymeleaf.TelegramTemplateProcessor.process("
            + "io.github.yvasyliev.forwarder.telegram.thymeleaf.TemplateContext))")
    public void processTemplate() {}
}
