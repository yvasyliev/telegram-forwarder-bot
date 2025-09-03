package io.github.yvasyliev.telegramforwarder.thymeleaf;

import org.thymeleaf.context.AbstractContext;

import java.util.function.Consumer;

@FunctionalInterface
public interface TemplateContextCustomizer extends Consumer<AbstractContext> {
}
