package io.github.yvasyliev.forwarder.telegram.x.util;

import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class XDescriptionParserTest {
    private static final XDescriptionParser PARSER = new XDescriptionParser();

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testParse = () -> Stream.of(
            arguments(
                    """
                    <p>Title</p>
                    <br>Video
                    <img src="https://image1"/>
                    <img src="https://image2"/>
                    """,
                    new XDescription("Title", true, List.of("https://image1", "https://image2"))
            ),
            arguments(
                    """
                    <img src="https://image1"/>
                    """,
                    new XDescription(null, false, List.of("https://image1"))
            )
    );

    @ParameterizedTest
    @FieldSource
    void testParse(String html, XDescription expected) {
        var actual = PARSER.parse(html);

        assertEquals(expected, actual);
    }
}
