package io.github.yvasyliev.telegramforwarder.reddit.deser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonTokenId;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditedDeserializerTest {
    private static final ValueDeserializer<Instant> EDITED_DESERIALIZER = new EditedDeserializer();
    @Mock private JsonParser p;
    @Mock private DeserializationContext ctxt;

    @Test
    void shouldReturnInstant() {
        var now = System.currentTimeMillis();
        var expected = Instant.ofEpochMilli(now);

        when(p.currentTokenId()).thenReturn(JsonTokenId.ID_NUMBER_INT);
        when(p.getLongValue()).thenReturn(now);

        var actual = assertDoesNotThrow(() -> EDITED_DESERIALIZER.deserialize(p, ctxt));

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnNull() {
        when(p.currentTokenId()).thenReturn(JsonTokenId.ID_FALSE);

        var actual = assertDoesNotThrow(() -> EDITED_DESERIALIZER.deserialize(p, ctxt));

        assertNull(actual);
    }
}
