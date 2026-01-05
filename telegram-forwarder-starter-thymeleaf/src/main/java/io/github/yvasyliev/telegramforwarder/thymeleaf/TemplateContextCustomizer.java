package io.github.yvasyliev.telegramforwarder.thymeleaf;

import org.thymeleaf.context.AbstractContext;

/**
 * A functional interface for customizing the Thymeleaf template context. Implementations of this interface can modify
 * the provided {@link AbstractContext} to add or change variables, settings, or other context-related information
 * before the template is processed.
 */
@FunctionalInterface
public interface TemplateContextCustomizer {
    /**
     * Customize the given Thymeleaf template context.
     *
     * @param abstractContext the Thymeleaf template context to customize
     */
    void customize(AbstractContext abstractContext);
}
