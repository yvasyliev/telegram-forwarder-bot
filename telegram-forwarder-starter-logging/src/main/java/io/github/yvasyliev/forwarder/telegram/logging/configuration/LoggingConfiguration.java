package io.github.yvasyliev.forwarder.telegram.logging.configuration;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.filter.Filter;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import io.github.yvasyliev.forwarder.telegram.logging.TelegramBotAppender;
import io.github.yvasyliev.forwarder.telegram.logging.mapper.LoggingSendMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Logging configuration.
 */
@Configuration
@EnableConfigurationProperties(TelegramBotAppenderProperties.class)
public class LoggingConfiguration {
    /**
     * A Telegram Bot Appender bean.
     *
     * @param adminProperties   the Telegram admin properties
     * @param sendMessageMapper the logging send message mapper
     * @param telegramClient    the Telegram client
     * @param context           the logger context
     * @param filter            the logging event filter
     * @return the Telegram Bot Appender
     */
    @Bean(initMethod = "start")
    @ConditionalOnMissingBean
    public Appender<ILoggingEvent> telegramBotAppender(
            TelegramAdminProperties adminProperties,
            LoggingSendMessageMapper sendMessageMapper,
            TelegramClient telegramClient,
            LoggerContext context,
            Filter<ILoggingEvent> filter
    ) {
        var appender = new TelegramBotAppender(adminProperties, sendMessageMapper, telegramClient);

        appender.setContext(context);
        appender.setName("telegramBotAppender");
        appender.addFilter(filter);

        context.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(appender);

        return appender;
    }

    /**
     * A Telegram Bot Appender Layout bean.
     *
     * @param botAppenderProperties the Telegram Bot Appender properties
     * @param loggerContext         the logger context
     * @return the Telegram Bot Appender Layout
     */
    @Bean(initMethod = "start")
    @ConditionalOnMissingBean(name = "telegramBotAppenderLayout")
    public PatternLayout telegramBotAppenderLayout(
            TelegramBotAppenderProperties botAppenderProperties,
            LoggerContext loggerContext
    ) {
        var patternLayout = new PatternLayout();

        patternLayout.setPattern(botAppenderProperties.pattern());
        patternLayout.setContext(loggerContext);

        return patternLayout;
    }

    /**
     * A Logger Context bean.
     *
     * @return the Logger Context
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggerContext loggerContext() {
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    /**
     * A Telegram Bot Appender Filter bean.
     *
     * @param botAppenderProperties the Telegram Bot Appender properties
     * @param loggerContext         the logger context
     * @return the Telegram Bot Appender Filter
     */
    @Bean(initMethod = "start")
    @ConditionalOnMissingBean(name = "telegramBotAppenderFilter")
    public Filter<ILoggingEvent> telegramBotAppenderFilter(

            TelegramBotAppenderProperties botAppenderProperties,
            LoggerContext loggerContext
    ) {
        var filter = new ThresholdFilter();

        filter.setLevel(botAppenderProperties.level());
        filter.setContext(loggerContext);
        filter.setName("telegramBotAppenderThresholdFilter");

        return filter;
    }
}
