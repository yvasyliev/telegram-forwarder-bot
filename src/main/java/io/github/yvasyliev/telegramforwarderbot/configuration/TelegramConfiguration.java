package io.github.yvasyliev.telegramforwarderbot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yvasyliev.telegramforwarderbot.TelegramForwarderBot;
import io.github.yvasyliev.telegramforwarderbot.service.TelegramTemplateEngine;
import io.github.yvasyliev.telegramforwarderbot.service.command.HelpMessageCommand;
import io.github.yvasyliev.telegramforwarderbot.service.command.MessageCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.expression.EvaluationContext;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.util.TelegramOkHttpClientFactory;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.thymeleaf.spring6.expression.ThymeleafEvaluationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@RequiredArgsConstructor
public class TelegramConfiguration {
    private final TelegramProperties telegramProperties;

    @Bean(defaultCandidate = false)
    public ScheduledExecutorService telegramScheduledExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

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

    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(telegramProperties.bot().token());
    }

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

    @Bean({"/help", "/start"})
    public MessageCommand helpMessageCommand(TelegramTemplateEngine templateEngine, TelegramClient telegramClient) {
        return new HelpMessageCommand(templateEngine, telegramClient);
    }

    @Bean
    public EvaluationContext evaluationContext(ApplicationContext applicationContext) {
        return new ThymeleafEvaluationContext(applicationContext, new DefaultConversionService());
    }
}
