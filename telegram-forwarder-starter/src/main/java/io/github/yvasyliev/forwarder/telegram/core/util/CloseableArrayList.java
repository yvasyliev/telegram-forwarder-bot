package io.github.yvasyliev.forwarder.telegram.core.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * An ArrayList that implements Closeable, allowing all its Closeable elements to be closed when the list is closed.
 *
 * @param <T> the type of elements in this list, which must implement Closeable
 */
public class CloseableArrayList<T extends Closeable> extends ArrayList<T> implements Closeable {
    @Override
    public void close() throws IOException {
        var e = stream()
                .map(this::tryClose)
                .filter(Objects::nonNull)
                .reduce((primaryThrowable, otherThrowable) -> {
                    primaryThrowable.addSuppressed(otherThrowable);
                    return primaryThrowable;
                })
                .map(ex -> ex instanceof IOException ioEx ? ioEx : new IOException(ex));

        if (e.isPresent()) {
            throw e.get();
        }
    }

    private Exception tryClose(T closeable) {
        try (var _ = closeable) {
            return null;
        } catch (Exception e) {
            return e;
        }
    }
}
