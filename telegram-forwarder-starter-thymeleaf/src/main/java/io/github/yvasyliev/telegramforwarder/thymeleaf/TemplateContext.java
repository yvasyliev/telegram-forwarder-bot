package io.github.yvasyliev.telegramforwarder.thymeleaf;

import org.thymeleaf.context.IContext;

/**
 * A record that holds a Thymeleaf template and its associated context.
 *
 * @param template the Thymeleaf template as a String
 * @param context  the Thymeleaf context containing variables and settings for template processing
 */
public record TemplateContext(String template, IContext context) {}
