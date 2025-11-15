package io.github.yvasyliev.telegramforwarder.bot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yvasyliev.telegramforwarder.bot.TelegramForwarderBot;
import io.github.yvasyliev.telegramforwarder.bot.service.command.HelpMessageCommand;
import io.github.yvasyliev.telegramforwarder.bot.service.command.MessageCommand;
import io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateEngine;
import io.github.yvasyliev.telegramforwarder.thymeleaf.TemplateContextCustomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.TelegramOkHttpClientFactory;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Configuration class for setting up the Telegram bot and its dependencies.
 */
@Configuration
@RequiredArgsConstructor
public class TelegramConfiguration {
    private final TelegramProperties telegramProperties;

    /**
     * Creates a {@link ScheduledExecutorService} bean for scheduling tasks related to the Telegram bot.
     * This bean is not a default candidate, meaning it won't be picked up automatically by Spring.
     *
     * @return a {@link ScheduledExecutorService} instance
     */
    @Bean(defaultCandidate = false)
    public ScheduledExecutorService telegramScheduledExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Creates a {@link TelegramBotsLongPollingApplication} bean for handling long polling updates from the Telegram
     * bot.
     *
     * @param objectMapper             the {@link ObjectMapper} for JSON serialization/deserialization
     * @param scheduledExecutorService the {@link ScheduledExecutorService} for scheduling tasks
     * @return a {@link TelegramBotsLongPollingApplication} instance
     */
    @Bean
    public TelegramBotsLongPollingApplication telegramBotsLongPollingApplication(
            ObjectMapper objectMapper,
            @Qualifier("telegramScheduledExecutorService") ScheduledExecutorService scheduledExecutorService
    ) {
        return new TelegramBotsLongPollingApplication(
                () -> objectMapper,
                new TelegramOkHttpClientFactory.DefaultOkHttpClientCreator(),
                () -> scheduledExecutorService
        );
    }

    /**
     * Creates a {@link TelegramClient} bean for interacting with the Telegram API.
     *
     * @return a {@link TelegramClient} instance
     */
    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(telegramProperties.bot().token());
    }

    /**
     * Creates a {@link TelegramForwarderBot} bean that handles updates from the Telegram bot.
     *
     * @param updateConsumer the {@link LongPollingUpdateConsumer} for processing updates
     * @param telegramClient the {@link TelegramClient} for sending messages
     * @param templateEngine the {@link TelegramTemplateEngine} for processing templates
     * @return a {@link TelegramForwarderBot} instance
     * @throws TelegramApiException if an error occurs while getting bot information
     */
    @Bean
    public TelegramForwarderBot telegramForwarderBot(
            LongPollingUpdateConsumer updateConsumer,
            TelegramClient telegramClient,
            TelegramTemplateEngine templateEngine
    ) throws TelegramApiException {
        return new TelegramForwarderBot(
                telegramProperties.bot().token(),
                updateConsumer,
                telegramClient.execute(new GetMe()),
                telegramProperties.adminId(),
                templateEngine,
                telegramClient
        );
    }

    /**
     * Creates a {@link MessageCommand} bean for handling the {@code /help} and {@code /start} commands.
     *
     * @param templateEngine the {@link TelegramTemplateEngine} for processing templates
     * @param telegramClient the {@link TelegramClient} for sending messages
     * @return a {@link MessageCommand} instance that handles help and start commands
     */
    @Bean({"/help", "/start"})
    public MessageCommand helpMessageCommand(TelegramTemplateEngine templateEngine, TelegramClient telegramClient) {
        return new HelpMessageCommand(templateEngine, telegramClient);
    }

    /**
     * Creates a {@link TemplateContextCustomizer} bean that sets the Telegram properties in the template context.
     *
     * @return a {@link TemplateContextCustomizer} instance that sets Telegram properties
     */
    @Bean
    public TemplateContextCustomizer telegramPropertiesSetter() {
        return context -> context.setVariable("telegramProperties", telegramProperties);
    }
}
