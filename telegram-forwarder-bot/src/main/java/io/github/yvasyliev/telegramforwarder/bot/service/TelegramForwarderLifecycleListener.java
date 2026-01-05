package io.github.yvasyliev.telegramforwarder.bot.service;

import io.github.yvasyliev.telegramforwarder.bot.mapper.SendMessageMapper;
import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramAdminProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Service that listens to application lifecycle events and sends notifications to the Telegram admin when the
 * application starts up or shuts down.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramForwarderLifecycleListener {
    private final TelegramAdminProperties adminProperties;
    private final SendMessageMapper sendMessageMapper;
    private final TelegramClient telegramClient;

    /**
     * Sends a startup notification to the Telegram admin when the application has started.
     */
    @EventListener(ApplicationStartedEvent.class)
    public void sendStartupNotification() {
        sendNotification("running");
    }

    /**
     * Sends a shutdown notification to the Telegram admin when the application is shutting down.
     */
    @EventListener(ContextClosedEvent.class)
    public void sendShutdownNotification() {
        sendNotification("shutdown");
    }

    private void sendNotification(String template) {
        var sendMessage = sendMessageMapper.map(adminProperties, template);

        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Failed to send notification message: {}", template, e);
        }
    }
}
