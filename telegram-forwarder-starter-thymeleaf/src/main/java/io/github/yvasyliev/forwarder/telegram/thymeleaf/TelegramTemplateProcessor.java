package io.github.yvasyliev.forwarder.telegram.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.thymeleaf.ITemplateEngine;

/**
 * A processor that uses a Thymeleaf template engine to process Telegram templates.
 */
@RequiredArgsConstructor
public class TelegramTemplateProcessor {
    private final ITemplateEngine templateEngine;

    /**
     * Processes the given template context using the Thymeleaf template engine.
     *
     * @param templateContext the template context containing the template and its associated context
     * @return the processed template as a String
     */
    public String process(TemplateContext templateContext) {
        return templateEngine.process(templateContext.template(), templateContext.context());
    }
}
