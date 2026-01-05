package io.github.yvasyliev.telegramforwarder.core.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.Closeable;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CloseableArrayListTest {
    private final CloseableArrayList<Closeable> closeables = new CloseableArrayList<>();
    @Mock private Closeable closeable1;
    @Mock private Closeable closeable2;

    @BeforeEach
    void setUp() {
        closeables.add(closeable1);
        closeables.add(closeable2);
    }

    @AfterEach
    void tearDown() throws IOException {
        verify(closeable1).close();
        verify(closeable2).close();
    }

    @Test
    void shouldCloseNormally() {
        assertDoesNotThrow(closeables::close);
    }

    @Test
    void shouldThrowIOExceptionIfTheFirstCloseableThrowsIOException() throws IOException {
        var e = new IOException("Close failed");

        doThrow(e).when(closeable1).close();

        assertThrows(IOException.class, closeables::close);
    }

    @Test
    void shouldThrowIOExceptionIfTheSecondCloseableThrowsIOException() throws IOException {
        var e = new IOException("Close failed");

        doThrow(e).when(closeable2).close();

        assertThrows(IOException.class, closeables::close);
    }

    @Test
    void shouldThrowIOExceptionIfTwoCloseablesThrowIOException() throws IOException {
        var e1 = new IOException("Close1 failed");
        var e2 = new IOException("Close2 failed");

        doThrow(e1).when(closeable1).close();
        doThrow(e2).when(closeable2).close();

        assertThrows(IOException.class, closeables::close);
    }

    @Test
    void shouldThrowIOExceptionIfTheFirstCloseableThrowsRuntimeException() throws IOException {
        var e = new RuntimeException("Close failed");

        doThrow(e).when(closeable1).close();

        assertThrows(IOException.class, closeables::close);
    }
}
