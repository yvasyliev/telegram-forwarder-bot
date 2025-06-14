package io.github.yvasyliev.telegramforwarderbot.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Set;

@ConfigurationProperties(prefix = "telegram")
@Validated
public record TelegramProperties(
        @NotNull Long adminId,
        @NotBlank String channelUsername,
        @DefaultValue("10_000") @NotNull Integer maxPhotoDimensions,
        @DefaultValue("10") @NotNull Integer mediaGroupMaxSize,
        @Validated @NotNull Bot bot,
        @Validated @NotNull PostControls postControls,
        @DefaultValue(Character.MAX_RADIX + StringUtils.EMPTY) @NotNull Integer radix,
        @NotBlank String unauthorizedActionText
) {
    public record Bot(@NotBlank String token, @NotBlank String version) {
    }

    public record PostControls(
            @NotEmpty Set<String> ignoredApiResponses,
            @NotBlank String initialMessageText,
            @Validated @NotEmpty Map<String, Button> buttons
    ) {
        public record Button(
                @NotBlank String buttonText,
                @NotBlank String callbackAnswerText,
                @NotBlank String messageText
        ) {}
    }
}
