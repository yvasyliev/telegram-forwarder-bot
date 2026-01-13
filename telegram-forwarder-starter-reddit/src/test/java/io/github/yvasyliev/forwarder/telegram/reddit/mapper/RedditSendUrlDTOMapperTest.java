package io.github.yvasyliev.forwarder.telegram.reddit.mapper;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendUrlDTO;
import io.github.yvasyliev.forwarder.telegram.reddit.dto.Link;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;
import org.mapstruct.factory.Mappers;

import java.net.URL;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RedditSendUrlDTOMapperTest {
    private static final RedditSendUrlDTOMapper SEND_URL_DTO_MAPPER = Mappers.getMapper(RedditSendUrlDTOMapper.class);

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testMap = () -> {
        var post = mock(Link.class);
        var text = "some title";
        var url = mock(URL.class);
        var expected = new SendUrlDTO(url, text);

        when(post.title()).thenReturn(text);
        when(post.url()).thenReturn(url);

        return Stream.of(arguments(null, null), arguments(post, expected));
    };

    @ParameterizedTest
    @FieldSource
    void testMap(Link post, SendUrlDTO expected) {
        var actual = SEND_URL_DTO_MAPPER.map(post);

        assertEquals(expected, actual);
    }
}
