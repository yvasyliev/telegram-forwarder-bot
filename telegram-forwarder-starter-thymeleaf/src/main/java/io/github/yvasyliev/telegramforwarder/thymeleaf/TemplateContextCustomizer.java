package io.github.yvasyliev.telegramforwarder.thymeleaf;

import org.thymeleaf.context.AbstractContext;

import java.util.function.Consumer;

/**
 * A functional interface for customizing the Thymeleaf template context. Implementations of this interface can modify
 * the provided {@link AbstractContext} to add or change variables, settings, or other context-related information
 * before the template is processed.
 */
@FunctionalInterface
public interface TemplateContextCustomizer extends Consumer<AbstractContext> {
}
