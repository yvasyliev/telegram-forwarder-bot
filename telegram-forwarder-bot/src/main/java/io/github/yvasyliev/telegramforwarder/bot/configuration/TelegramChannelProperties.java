package io.github.yvasyliev.telegramforwarder.bot.configuration;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration properties for the Telegram channel.
 *
 * @param chatId the chat ID of the Telegram channel
 */
@ConfigurationProperties("telegram.channel")
@Validated
public record TelegramChannelProperties(@NotBlank String chatId) {}
