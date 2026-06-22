package io.github.yvasyliev.forwarder.telegram.x.core.io;

import io.github.yvasyliev.forwarder.telegram.x.io.ByteFilteringInputStream;
import lombok.EqualsAndHashCode;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

/**
 * Custom {@link UrlResource} that allows setting a custom {@code User-Agent} header when opening a connection to the
 * URL.
 */
@EqualsAndHashCode(callSuper = true)
public class RssUrlResource extends UrlResource {
    private final String userAgent;
    private final Set<Byte> bytesToFilter;

    /**
     * Creates a new instance of {@link RssUrlResource} with the given URL and {@code User-Agent} header.
     *
     * @param url           the URL to access
     * @param userAgent     the {@code User-Agent} header to use when making requests to the URL
     * @param bytesToFilter the set of bytes to filter from the response when fetching posts from the URL
     */
    public RssUrlResource(URL url, String userAgent, Set<Byte> bytesToFilter) {
        super(url);
        this.userAgent = userAgent;
        this.bytesToFilter = bytesToFilter;
    }

    @Override
    protected void customizeConnection(URLConnection con) throws IOException {
        super.customizeConnection(con);
        con.setRequestProperty(HttpHeaders.USER_AGENT, userAgent);
    }

    /**
     * Returns an input stream that filters out {@code null} bytes (Unicode {@code 0x0}) which are invalid in XML. This
     * prevents parsing errors when the feed contains corrupt data with embedded {@code null} bytes.
     *
     * @return a filtered input stream with {@code null} bytes removed
     * @throws IOException if an I/O error occurs
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteFilteringInputStream(super.getInputStream(), bytesToFilter);
    }
}
