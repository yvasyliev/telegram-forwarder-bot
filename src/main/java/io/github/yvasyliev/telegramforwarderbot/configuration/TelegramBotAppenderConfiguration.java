package io.github.yvasyliev.telegramforwarderbot.configuration;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.filter.Filter;
import io.github.yvasyliev.telegramforwarderbot.appender.TelegramBotAppender;
import io.github.yvasyliev.telegramforwarderbot.service.TelegramTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Configuration for the Telegram Bot Appender that sends log messages to a Telegram chat.
 * The appender is enabled by default and can be disabled by setting
 * {@code logging.telegram-bot-appender.enabled=false} property.
 */
@Configuration
@ConditionalOnProperty(name = "logging.telegram-bot-appender.enabled", havingValue = "true", matchIfMissing = true)
public class TelegramBotAppenderConfiguration {
    /**
     * Creates a Telegram Bot Appender bean that sends log messages to a Telegram chat.
     *
     * @param telegramClient     the Telegram client
     * @param templateEngine     the template engine for formatting messages
     * @param telegramProperties properties containing the admin ID
     * @param layout             the layout for formatting log messages
     * @param loggerContext      the logger context
     * @param filter             the filter for log events
     * @return the configured Telegram Bot Appender
     */
    @Bean(initMethod = "start")
    public Appender<ILoggingEvent> telegramBotAppender(
            TelegramClient telegramClient,
            TelegramTemplateEngine templateEngine,
            TelegramProperties telegramProperties,
            Layout<ILoggingEvent> layout,
            LoggerContext loggerContext,
            Filter<ILoggingEvent> filter
    ) {
        var telegramBotAppender = new TelegramBotAppender(
                telegramClient,
                templateEngine,
                telegramProperties.adminId(),
                layout
        );
        telegramBotAppender.setName("telegramBotAppender");
        telegramBotAppender.setContext(loggerContext);
        telegramBotAppender.addFilter(filter);

        loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(telegramBotAppender);

        return telegramBotAppender;
    }

    /**
     * Creates a layout for formatting log messages sent to the Telegram Bot Appender.
     *
     * @param properties    the properties containing the pattern for log messages
     * @param loggerContext the logger context
     * @return the configured layout
     */
    @Bean(initMethod = "start")
    public Layout<ILoggingEvent> layout(TelegramBotAppenderProperties properties, LoggerContext loggerContext) {
        var patternLayout = new PatternLayout();
        patternLayout.setPattern(properties.pattern());
        patternLayout.setContext(loggerContext);
        return patternLayout;
    }

    /**
     * Provides the logger context for the Telegram Bot Appender.
     *
     * @return the logger context
     */
    @Bean
    public LoggerContext loggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    /**
     * Creates a filter for the Telegram Bot Appender that filters log events based on the specified level.
     *
     * @param properties    the properties containing the log level threshold
     * @param loggerContext the logger context
     * @return the configured filter
     */
    @Bean(initMethod = "start")
    public Filter<ILoggingEvent> filter(TelegramBotAppenderProperties properties, LoggerContext loggerContext) {
        var thresholdFilter = new ThresholdFilter();
        thresholdFilter.setLevel(properties.level());
        thresholdFilter.setContext(loggerContext);
        thresholdFilter.setName("telegramBotAppenderThresholdFilter");
        return thresholdFilter;
    }
}
