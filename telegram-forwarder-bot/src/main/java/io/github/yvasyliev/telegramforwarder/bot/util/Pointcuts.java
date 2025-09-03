package io.github.yvasyliev.telegramforwarder.bot.util;

import io.github.yvasyliev.telegramforwarder.bot.dto.AbstractCommandCallbackDataDTO;
import io.github.yvasyliev.telegramforwarder.bot.service.TelegramEventHandler;
import io.github.yvasyliev.telegramforwarder.bot.service.command.CallbackQueryCommand;
import io.github.yvasyliev.telegramforwarder.bot.service.command.PostControlsCallbackQueryCommand;
import org.aspectj.lang.annotation.Pointcut;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.message.Message;

/**
 * Defines pointcuts for AOP in the Telegram Forwarder Bot application.
 *
 * <p>
 * This class contains pointcuts that match specific method executions related to
 * handling Telegram events, executing commands, and sending messages.
 */
public class Pointcuts {
    /**
     * Pointcut for handling Telegram message events.
     * Matches the execution of the {@link TelegramEventHandler#handle(BotApiObject)} method for{@link Message} objects.
     */
    @Pointcut("execution(void io.github.yvasyliev.telegramforwarder.bot.service.TelegramEventHandler.handle("
            + "org.telegram.telegrambots.meta.api.objects.message.Message))")
    public void handleMessageEvent() {
    }

    /**
     * Pointcut for handling Telegram callback query events.
     * Matches the execution of the {@link TelegramEventHandler#handle(BotApiObject)} method for
     * {@link CallbackQuery} objects.
     */
    @Pointcut("execution(void io.github.yvasyliev.telegramforwarder.bot.service.TelegramEventHandler.handle("
            + "org.telegram.telegrambots.meta.api.objects.CallbackQuery))")
    public void handleCallbackQueryEvent() {
    }

    /**
     * Pointcut for executing a command that handles a Telegram message.
     * Matches the execution of the
     * {@link CallbackQueryCommand#execute(CallbackQuery, AbstractCommandCallbackDataDTO)} method.
     */
    @Pointcut("execution(void io.github.yvasyliev.telegramforwarder.bot.service.command.CallbackQueryCommand.execute("
            + "org.telegram.telegrambots.meta.api.objects.CallbackQuery, "
            + "io.github.yvasyliev.telegramforwarder.bot.dto.AbstractCommandCallbackDataDTO))")
    public void executeCallbackQueryCommand() {
    }

    /**
     * Pointcut for sending a post message.
     * Matches the execution of methods in the sender package that send a single message.
     */
    @Pointcut("execution(org.telegram.telegrambots.meta.api.objects.message.Message "
            + "io.github.yvasyliev.telegramforwarder.core.service.PostSender.send(..))")
    public void sendPost() {
    }

    /**
     * Pointcut for sending a media group.
     * Matches the execution of methods in the sender package that send a list of messages.
     */
    @Pointcut("execution(java.util.List<org.telegram.telegrambots.meta.api.objects.message.Message> "
            + "io.github.yvasyliev.telegramforwarder.core.service.PostSender.send(..))")
    public void sendMediaGroup() {
    }

    /**
     * Pointcut for executing the
     * {@link PostControlsCallbackQueryCommand#execute(CallbackQuery, AbstractCommandCallbackDataDTO)} method.
     */
    @Pointcut("execution(void "
            + "io.github.yvasyliev.telegramforwarder.bot.service.command.PostControlsCallbackQueryCommand.execute("
            + "org.telegram.telegrambots.meta.api.objects.CallbackQuery, *))")
    public void executePostControlsCallbackQueryCommand() {
    }
}
