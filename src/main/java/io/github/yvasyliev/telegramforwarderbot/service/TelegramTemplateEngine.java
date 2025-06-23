package io.github.yvasyliev.telegramforwarderbot.service;

import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramBotAppenderProperties;
import io.github.yvasyliev.telegramforwarderbot.configuration.TelegramProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.AbstractContext;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.expression.ThymeleafEvaluationContext;

/**
 * Service for processing Telegram templates using Thymeleaf.
 */
@Service
@RequiredArgsConstructor
public class TelegramTemplateEngine {
    private final TelegramProperties telegramProperties;
    private final TelegramBotAppenderProperties appenderProperties;
    private final EvaluationContext evaluationContext;
    private final ITemplateEngine templateEngine;

    /**
     * Processes the given template with the empty context.
     *
     * @param template the template to process
     * @return the processed template as a String
     */
    public String process(String template) {
        return process(template, new Context());
    }

    /**
     * Processes the given template with the provided context.
     * This method sets the Telegram properties and appender properties as variables in the context.
     *
     * @param template the template to process
     * @param context  the context to use for processing
     * @return the processed template as a String
     */
    public String process(String template, AbstractContext context) {
        context.setVariable("telegramProperties", telegramProperties);
        context.setVariable("appenderProperties", appenderProperties);
        context.setVariable(
                ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                evaluationContext
        );

        return templateEngine.process(template, context);
    }
}
