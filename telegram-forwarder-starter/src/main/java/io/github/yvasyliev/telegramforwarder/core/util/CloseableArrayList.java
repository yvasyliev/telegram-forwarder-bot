package io.github.yvasyliev.telegramforwarder.core.util;

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
                .map(t -> t instanceof IOException ex ? ex : new IOException(t));

        if (e.isPresent()) {
            throw e.get();
        }
    }

    private Throwable tryClose(T closeable) {
        try (var ignored = closeable) { // TODO: Java 25
            return null;
        } catch (Throwable t) {
            return t;
        }
    }
}
