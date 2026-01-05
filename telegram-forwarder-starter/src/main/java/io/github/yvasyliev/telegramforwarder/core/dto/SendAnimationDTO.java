package io.github.yvasyliev.telegramforwarder.core.dto;

import lombok.experimental.Delegate;

import java.io.Closeable;

/**
 * DTO for sending animation files via Telegram.
 *
 * @param animation  the animation file to be sent
 * @param caption    the caption for the animation
 * @param hasSpoiler indicates if the animation has a spoiler
 */
public record SendAnimationDTO(
        @Delegate(types = Closeable.class) InputFileDTO animation,
        String caption,
        boolean hasSpoiler
) implements Closeable {}
