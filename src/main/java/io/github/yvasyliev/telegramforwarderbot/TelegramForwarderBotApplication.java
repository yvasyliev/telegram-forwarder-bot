package io.github.yvasyliev.telegramforwarderbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConfigurationPropertiesScan
@EnableAspectJAutoProxy
@EnableAsync
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class TelegramForwarderBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(TelegramForwarderBotApplication.class, args);
    }
}
