package io.github.yvasyliev.telegramforwarder.bot.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Set;

/**
 * Configuration properties for the Telegram Forwarder Bot.
 *
 * @param adminId                ID of the admin user
 * @param channelUsername        username of the Telegram channel
 * @param bot                    bot configuration including token and version
 * @param postControls           controls for posting messages and buttons
 * @param radix                  radix for number formatting
 * @param unauthorizedActionText text displayed when an unauthorized action is attempted
 */
@ConfigurationProperties(prefix = "telegram")
@Validated
public record TelegramProperties(
        @NotNull Long adminId,
        @NotBlank String channelUsername,
        @Validated @NotNull Bot bot,
        @Validated @NotNull PostControls postControls,
        @DefaultValue(Character.MAX_RADIX + StringUtils.EMPTY) @NotNull Integer radix,
        @NotBlank String unauthorizedActionText
) {
    /**
     * Configuration for the Telegram Bot.
     *
     * @param token   the bot token
     * @param version the bot version
     */
    public record Bot(@NotBlank String token, @NotBlank String version) {
    }

    /**
     * Configuration for post controls.
     *
     * @param ignoredApiResponses set of API responses to ignore
     * @param initialMessageText  initial text for messages
     * @param buttons             map of button identifiers to their properties
     */
    public record PostControls(
            @NotEmpty Set<String> ignoredApiResponses,
            @NotBlank String initialMessageText,
            @Validated @NotEmpty Map<String, Button> buttons
    ) {
        /**
         * Configuration for a button in the post controls.
         *
         * @param buttonText         text displayed on the button
         * @param callbackAnswerText text sent as a callback answer
         * @param messageText        text sent in the message when the button is pressed
         */
        public record Button(
                @NotBlank String buttonText,
                @NotBlank String callbackAnswerText,
                @NotBlank String messageText
        ) {}
    }
}
