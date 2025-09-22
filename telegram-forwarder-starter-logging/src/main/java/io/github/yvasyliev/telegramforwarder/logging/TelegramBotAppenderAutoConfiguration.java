package io.github.yvasyliev.telegramforwarder.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.filter.Filter;
import io.github.yvasyliev.telegramforwarder.thymeleaf.TelegramTemplateEngine;
import io.github.yvasyliev.telegramforwarder.thymeleaf.TemplateContextCustomizer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Configuration for the Telegram Bot Appender that sends log messages to a Telegram chat.
 * The appender is enabled by default and can be disabled by setting
 * {@code logging.telegram-bot-appender.enabled=false} property.
 */
@AutoConfiguration
@ConditionalOnProperty(name = "logging.telegram-bot-appender.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(TelegramBotAppenderProperties.class)
@RequiredArgsConstructor
public class TelegramBotAppenderAutoConfiguration {
    private final TelegramBotAppenderProperties botAppenderProperties;

    /**
     * Creates a Telegram Bot Appender bean that sends log messages to a Telegram chat.
     *
     * @param telegramClient            the Telegram client
     * @param templateEngine            the template engine for formatting messages
     * @param telegramBotAppenderLayout the layout for formatting log messages
     * @param loggerContext             the logger context
     * @param telegramBotAppenderFilter the filter for log events
     * @return the configured Telegram Bot Appender
     */
    @Bean(initMethod = "start")
    @ConditionalOnMissingBean
    public Appender<ILoggingEvent> telegramBotAppender(
            TelegramClient telegramClient,
            TelegramTemplateEngine templateEngine,
            Layout<ILoggingEvent> telegramBotAppenderLayout,
            LoggerContext loggerContext,
            Filter<ILoggingEvent> telegramBotAppenderFilter
    ) {
        var telegramBotAppender = new TelegramBotAppender(
                telegramClient,
                templateEngine,
                botAppenderProperties.chatId(),
                telegramBotAppenderLayout
        );
        telegramBotAppender.setName("telegramBotAppender");
        telegramBotAppender.setContext(loggerContext);
        telegramBotAppender.addFilter(telegramBotAppenderFilter);

        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(telegramBotAppender);

        return telegramBotAppender;
    }

    /**
     * Creates a layout for formatting log messages sent to the Telegram Bot Appender.
     *
     * @param loggerContext the logger context
     * @return the configured layout
     */
    @Bean(initMethod = "start")
    @ConditionalOnMissingBean(name = "telegramBotAppenderLayout")
    public Layout<ILoggingEvent> telegramBotAppenderLayout(LoggerContext loggerContext) {
        var patternLayout = new PatternLayout();
        patternLayout.setPattern(botAppenderProperties.pattern());
        patternLayout.setContext(loggerContext);
        return patternLayout;
    }

    /**
     * Provides the logger context for the Telegram Bot Appender.
     *
     * @return the logger context
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggerContext loggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    /**
     * Creates a filter for the Telegram Bot Appender that filters log events based on the specified level.
     *
     * @param loggerContext the logger context
     * @return the configured filter
     */
    @Bean(initMethod = "start")
    @ConditionalOnMissingBean(name = "telegramBotAppenderFilter")
    public Filter<ILoggingEvent> telegramBotAppenderFilter(LoggerContext loggerContext) {
        var thresholdFilter = new ThresholdFilter();
        thresholdFilter.setLevel(botAppenderProperties.level());
        thresholdFilter.setContext(loggerContext);
        thresholdFilter.setName("telegramBotAppenderThresholdFilter");
        return thresholdFilter;
    }

    /**
     * Creates a {@link TemplateContextCustomizer} that sets the bot appender properties in the template context.
     *
     * @return the configured {@link TemplateContextCustomizer}
     */
    @Bean
    @ConditionalOnMissingBean(name = "telegramBotAppenderPropertiesSetter")
    public TemplateContextCustomizer telegramBotAppenderPropertiesSetter() {
        return context -> context.setVariable("botAppenderProperties", botAppenderProperties);
    }
}
