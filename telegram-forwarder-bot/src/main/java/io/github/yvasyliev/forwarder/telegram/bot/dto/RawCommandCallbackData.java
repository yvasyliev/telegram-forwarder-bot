package io.github.yvasyliev.forwarder.telegram.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.yvasyliev.forwarder.telegram.bot.databind.util.RadixDeserializeConverter;
import io.github.yvasyliev.forwarder.telegram.bot.databind.util.RadixSerializeConverter;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

/**
 * Data Transfer Object representing raw command callback data.
 *
 * @param command        the command associated with the callback
 * @param firstMessageId the ID of the first message related to the command
 * @param messageCount   the count of messages related to the command
 */
public record RawCommandCallbackData(
        @JsonProperty("c")
        String command,

        @JsonProperty("m")
        @JsonDeserialize(converter = RadixDeserializeConverter.class)
        @JsonSerialize(converter = RadixSerializeConverter.class)
        Integer firstMessageId,

        @JsonProperty("s")
        @JsonDeserialize(converter = RadixDeserializeConverter.class)
        @JsonSerialize(converter = RadixSerializeConverter.class)
        Integer messageCount
) {}
