package io.github.yvasyliev.telegramforwarderbot.reddit.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EditedDeserializerTest {
    private static final JsonDeserializer<Instant> EDITED_DESERIALIZER = new EditedDeserializer();
    @Mock private JsonParser p;
    @Mock private DeserializationContext ctxt;

    @Test
    void shouldReturnInstant() throws IOException {
        var expected = Instant.now();

        when(p.getCurrentToken()).thenReturn(JsonToken.VALUE_NUMBER_INT);
        when(ctxt.readValue(p, Instant.class)).thenReturn(expected);

        var actual = assertDoesNotThrow(() -> EDITED_DESERIALIZER.deserialize(p, ctxt));

        assertEquals(expected, actual);
        verify(ctxt).readValue(p, Instant.class);
    }

    @Test
    void shouldReturnNull() {
        when(p.getCurrentToken()).thenReturn(JsonToken.VALUE_FALSE);

        var actual = assertDoesNotThrow(() -> EDITED_DESERIALIZER.deserialize(p, ctxt));

        assertNull(actual);
        verifyNoInteractions(ctxt);
    }
}
