package io.github.yvasyliev.telegramforwarder.bot.util;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Defines pointcuts for AOP in the Telegram Forwarder Bot application.
 */
public class Pointcuts {
    /**
     * Pointcut for handling Telegram message events.
     */
    @Pointcut("execution(void io.github.yvasyliev.telegramforwarder.bot.service.TelegramEventHandler.handle("
            + "org.telegram.telegrambots.meta.api.objects.message.Message))")
    public void handleMessageEvent() {}

    /**
     * Pointcut for handling Telegram callback query events.
     */
    @Pointcut("execution(void io.github.yvasyliev.telegramforwarder.bot.service.TelegramEventHandler.handle("
            + "org.telegram.telegrambots.meta.api.objects.CallbackQuery))")
    public void handleCallbackQueryEvent() {}

    /**
     * Pointcut for executing a callback query command.
     */
    @Pointcut("execution(void io.github.yvasyliev.telegramforwarder.bot.service.command.CallbackQueryCommand.execute("
            + "org.telegram.telegrambots.meta.api.objects.CallbackQuery, "
            + "io.github.yvasyliev.telegramforwarder.bot.dto.CommandCallbackData))")
    public void executeCallbackQueryCommand() {}

    /**
     * Pointcut for sending a post message.
     */
    @Pointcut("execution(org.telegram.telegrambots.meta.api.objects.message.Message "
            + "io.github.yvasyliev.telegramforwarder.core.service.PostSender.send(..))")
    public void sendPost() {}

    /**
     * Pointcut for sending a media group.
     */
    @Pointcut("execution(java.util.List<org.telegram.telegrambots.meta.api.objects.message.Message> "
            + "io.github.yvasyliev.telegramforwarder.core.service.PostSender.send(..))")
    public void sendMediaGroup() {}

    /**
     * Pointcut for executing the post controls command.
     */
    @Pointcut("execution(void "
            + "io.github.yvasyliev.telegramforwarder.bot.service.command.PostControlsCallbackQueryCommand.execute("
            + "org.telegram.telegrambots.meta.api.objects.CallbackQuery, *))")
    public void executePostControlsCallbackQueryCommand() {}
}
