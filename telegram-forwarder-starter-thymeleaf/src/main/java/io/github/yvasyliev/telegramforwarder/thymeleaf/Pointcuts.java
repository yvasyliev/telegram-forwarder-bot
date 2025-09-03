package io.github.yvasyliev.telegramforwarder.thymeleaf;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateEngine.process*(..))")
    public void processTemplate() {
    }
}
