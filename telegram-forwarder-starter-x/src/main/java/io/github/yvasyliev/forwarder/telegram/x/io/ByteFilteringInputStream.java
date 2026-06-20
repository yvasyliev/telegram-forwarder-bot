package io.github.yvasyliev.forwarder.telegram.x.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * A custom input stream filter that removes {@code null} bytes ({@code 0x00}) from the stream.
 */
public class ByteFilteringInputStream extends FilterInputStream {
    private final Set<Byte> bytesToFilter;

    public ByteFilteringInputStream(InputStream in, Set<Byte> bytesToFilter) {
        super(in);
        this.bytesToFilter = bytesToFilter;
    }

    /**
     * Reads the next byte from the stream, skipping any {@code null} bytes.
     *
     * @return {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    @Override
    public int read() throws IOException {
        var read = in.read();

        while (bytesToFilter.contains((byte) read)) {
            read = in.read();
        }

        return read;
    }

    /**
     * Reads bytes from the stream into an array, filtering out {@code null} bytes.
     *
     * @param b   {@inheritDoc}
     * @param off {@inheritDoc}
     * @param len {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IOException {@inheritDoc}
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        var temp = new byte[len];
        var readCount = in.read(temp, 0, len);

        if (readCount <= 0) {
            return readCount;
        }

        var writePos = 0;

        for (var i = 0; i < readCount; i++) {
            if (!bytesToFilter.contains(temp[i])) {
                b[off + writePos++] = temp[i];
            }
        }

        return writePos > 0 ? writePos : read(b, off, len);
    }
}
