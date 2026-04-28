package io.github.yvasyliev.forwarder.telegram.x.core.io;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Custom {@link UrlResource} that allows setting a custom {@code User-Agent} header when opening a connection to the
 * URL.
 */
public class RssUrlResource extends UrlResource {
    private final String userAgent;

    /**
     * Creates a new instance of {@link RssUrlResource} with the given URL and {@code User-Agent} header.
     *
     * @param url       the URL to access
     * @param userAgent the {@code User-Agent} header to use when making requests to the URL
     */
    public RssUrlResource(URL url, String userAgent) {
        super(url);
        this.userAgent = userAgent;
    }

    @Override
    protected void customizeConnection(URLConnection con) throws IOException {
        super.customizeConnection(con);
        con.setRequestProperty(HttpHeaders.USER_AGENT, userAgent);
    }
}
