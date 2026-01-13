package io.github.yvasyliev.forwarder.telegram.bot.mapper;

import io.github.yvasyliev.forwarder.telegram.bot.configuration.TelegramChannelProperties;
import io.github.yvasyliev.forwarder.telegram.bot.entity.ApprovedPost;
import io.github.yvasyliev.forwarder.telegram.core.configuration.TelegramAdminProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;
import org.telegram.telegrambots.meta.api.methods.CopyMessages;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CopyMessagesMapperTest {
    private static final String CHAT_ID = "chat-id";
    private static final long ID = 123456789L;
    private static final int MESSAGE_ID = 987654321;
    private static final CopyMessagesMapper COPY_MESSAGES_MAPPER = Mappers.getMapper(CopyMessagesMapper.class);

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMap = () -> {
        var telegramChannelProperties = new TelegramChannelProperties(CHAT_ID);
        var telegramAdminProperties = new TelegramAdminProperties(ID);
        var post = new ApprovedPost();
        var expected = CopyMessages.builder()
                .chatId(CHAT_ID)
                .fromChatId(ID)
                .messageId(MESSAGE_ID)
                .removeCaption(true)
                .build();

        post.setMessageIds(List.of(MESSAGE_ID));
        post.setRemoveCaption(true);

        return Stream.of(
                arguments(null, null, null, null),
                arguments(telegramChannelProperties, telegramAdminProperties, post, expected)
        );
    };

    @ParameterizedTest
    @FieldSource
    void testMap(
            TelegramChannelProperties channelProperties,
            TelegramAdminProperties adminProperties,
            ApprovedPost post,
            CopyMessages expected
    ) {
        var actual = COPY_MESSAGES_MAPPER.map(channelProperties, adminProperties, post);

        assertEquals(expected, actual);
    }
}
