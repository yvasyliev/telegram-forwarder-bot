package io.github.yvasyliev.forwarder.telegram.x.io;

import lombok.Cleanup;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ByteFilteringInputStreamTest {
    private static final byte BYTE_TO_FILTER = 0x0;

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testRead = () -> Stream.of(
            arguments(new byte[]{0x1}, 0x1),
            arguments(new byte[]{0x0, 0x1}, 0x1),
            arguments(new byte[]{0x0, 0x0}, -1),
            arguments(new byte[]{0x1, 0x0}, 0x1)
    );

    @SuppressWarnings("checkstyle:ConstantName")
    private static final Supplier<Stream<Arguments>> testReadBuffer = () -> Stream.of(
            arguments(new byte[]{0x1}, 1, 1, new byte[]{0x7, 0x7, 0x7}, 1, new byte[]{0x7, 0x1, 0x7}),
            arguments(new byte[]{0x0, 0x1}, 1, 1, new byte[]{0x7, 0x7, 0x7}, 1, new byte[]{0x7, 0x1, 0x7}),
            arguments(new byte[]{0x0, 0x0}, 1, 1, new byte[]{0x7, 0x7, 0x7}, -1, new byte[]{0x7, 0x7, 0x7}),
            arguments(new byte[]{0x1, 0x0}, 1, 2, new byte[]{0x7, 0x7, 0x7, 0x7}, 1, new byte[]{0x7, 0x1, 0x7, 0x7}),
            arguments(
                    new byte[]{0x1, 0x2, 0x0},
                    1,
                    3,
                    new byte[]{0x7, 0x7, 0x7, 0x7, 0x7},
                    2,
                    new byte[]{0x7, 0x1, 0x2, 0x7, 0x7}
            )
    );

    @ParameterizedTest
    @FieldSource
    void testRead(byte[] bytes, int expected) throws IOException {
        @Cleanup var inputStream = new ByteFilteringInputStream(
                new ByteArrayInputStream(bytes),
                Set.of(BYTE_TO_FILTER)
        );

        var actual = assertDoesNotThrow(() -> inputStream.read());

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @FieldSource
    void testReadBuffer(byte[] bytes, int off, int len, byte[] buffer, int expected, byte[] expectedBuffer)
            throws IOException {
        @Cleanup var inputStream = new ByteFilteringInputStream(
                new ByteArrayInputStream(bytes),
                Set.of(BYTE_TO_FILTER)
        );

        var actual = assertDoesNotThrow(() -> inputStream.read(buffer, off, len));

        assertEquals(expected, actual);
        assertArrayEquals(expectedBuffer, buffer);
    }
}
