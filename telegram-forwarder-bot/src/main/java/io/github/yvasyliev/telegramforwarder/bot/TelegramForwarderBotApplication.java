package io.github.yvasyliev.telegramforwarder.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for the Telegram Forwarder Bot.
 * This class serves as the entry point for the Spring Boot application.
 * It enables various features such as aspect-oriented programming, asynchronous processing,
 * JPA auditing, and scheduling.
 */
@ConfigurationPropertiesScan
@EnableAsync
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class TelegramForwarderBotApplication {
    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(TelegramForwarderBotApplication.class, args);
    }
}
