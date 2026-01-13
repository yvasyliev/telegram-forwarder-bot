package io.github.yvasyliev.forwarder.telegram.bot.util;

import io.github.yvasyliev.forwarder.telegram.thymeleaf.TelegramTemplateProcessor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;

/**
 * Template processor used by the Telegram Bot.
 */
@Component
@Named(TelegramBotTemplateProcessor.NAME)
public class TelegramBotTemplateProcessor extends TelegramTemplateProcessor {
    /**
     * Template processor name.
     */
    public static final String NAME = "telegramBotTemplateProcessor";

    public TelegramBotTemplateProcessor(ITemplateEngine templateEngine) {
        super(templateEngine);
    }
}
