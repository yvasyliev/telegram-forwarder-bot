package io.github.yvasyliev.telegramforwarderbot.util;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(void io.github.yvasyliev.telegramforwarderbot.service.TelegramEventHandler.handle(org" +
            ".telegram.telegrambots.meta.api.objects.message.Message))")
    public void handleMessageEvent() {
    }

    @Pointcut("execution(void io.github.yvasyliev.telegramforwarderbot.service.TelegramEventHandler.handle(org" +
            ".telegram.telegrambots.meta.api.objects.CallbackQuery))")
    public void handleCallbackQueryEvent() {
    }

    @Pointcut("execution(void io.github.yvasyliev.telegramforwarderbot.service.command.CallbackQueryCommand.execute" +
            "(org.telegram.telegrambots.meta.api.objects.CallbackQuery, io.github.yvasyliev.telegramforwarderbot.dto" +
            ".AbstractCommandCallbackDataDTO))")
    public void executeCallbackQueryCommand() {
    }

    @Pointcut("execution(org.telegram.telegrambots.meta.api.objects.message.Message io.github.yvasyliev" +
            ".telegramforwarderbot.service.sender.*.send*(..))")
    public void sendPost() {
    }

    @Pointcut("execution(java.util.List<org.telegram.telegrambots.meta.api.objects.message.Message> io.github" +
            ".yvasyliev.telegramforwarderbot.service.sender.*.send*(..))")
    public void sendMediaGroup() {
    }

    @Pointcut("execution(void io.github.yvasyliev.telegramforwarderbot.service.command" +
            ".PostControlsCallbackQueryCommand.execute(org.telegram.telegrambots.meta.api.objects.CallbackQuery, *))")
    public void executePostControlsCallbackQueryCommand() {
    }
}
