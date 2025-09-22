package io.github.yvasyliev.telegramforwarder.thymeleaf;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Pointcuts for aspect-oriented programming (AOP) in the Thymeleaf integration module.
 */
public class Pointcuts {
    /**
     * Pointcut that matches the execution of any method starting with "process" in the
     * {@link io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateEngine} class.
     */
    @Pointcut("execution(* io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateEngine.process*(..))")
    public void processTemplate() {
    }
}
