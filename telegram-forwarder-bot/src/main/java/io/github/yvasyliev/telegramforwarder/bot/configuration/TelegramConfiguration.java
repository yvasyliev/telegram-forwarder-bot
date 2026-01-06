package io.github.yvasyliev.telegramforwarder.bot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yvasyliev.telegramforwarder.bot.mapper.BotDTOMapper;
import io.github.yvasyliev.telegramforwarder.bot.service.command.MessageCommand;
import io.github.yvasyliev.telegramforwarder.thymeleaf.TemplateContextCustomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
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
    private final TelegramBotProperties botProperties;

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
     * @param scheduledExecutorService the {@link ScheduledExecutorService} for scheduling tasks
     * @return a {@link TelegramBotsLongPollingApplication} instance
     */
    @Bean
    public TelegramBotsLongPollingApplication telegramBotsLongPollingApplication(
            @Qualifier("telegramScheduledExecutorService") ScheduledExecutorService scheduledExecutorService
    ) {
        return new TelegramBotsLongPollingApplication(
                ObjectMapper::new,
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
        return new OkHttpTelegramClient(botProperties.getBotToken());
    }

    /**
     * Creates a {@link MessageCommand} bean for the {@code /start} command that delegates to the {@code /help} command.
     *
     * @param helpMessageCommand the {@link MessageCommand} for the {@code /help} command
     * @return a {@link MessageCommand} instance for the {@code /start} command
     */
    @Bean("/start")
    public MessageCommand startMessageCommand(@Qualifier("/help") MessageCommand helpMessageCommand) {
        return helpMessageCommand;
    }

    /**
     * Creates a {@link TemplateContextCustomizer} bean that sets Telegram bot properties and channel properties
     * in the Thymeleaf template context.
     *
     * @param telegramClient    the {@link TelegramClient} for interacting with the Telegram API
     * @param botDTOMapper      the {@link BotDTOMapper} for mapping bot data
     * @param channelProperties the {@link TelegramChannelProperties} for channel configuration
     * @return a {@link TemplateContextCustomizer} instance
     * @throws TelegramApiException if there is an error retrieving bot information
     */
    @Bean
    public TemplateContextCustomizer telegramPropertiesSetter(
            TelegramClient telegramClient,
            BotDTOMapper botDTOMapper,
            TelegramChannelProperties channelProperties
    ) throws TelegramApiException {
        var bot = botDTOMapper.map(telegramClient.execute(new GetMe()), botProperties);

        return context -> {
            context.setVariable("bot", bot);
            context.setVariable("channel", channelProperties);
        };
    }
}
