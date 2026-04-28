package io.github.yvasyliev.forwarder.telegram.x.util;

import io.github.yvasyliev.forwarder.telegram.x.dto.XDescription;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.util.List;

/**
 * A description  parser of an X post.
 */
public class XDescriptionParser {
    /**
     * Parses the description of an X post from its HTML content.
     *
     * @param html the HTML content of the X post description
     * @return a parsed {@link XDescription}
     */
    public XDescription parse(String html) {
        var description = Jsoup.parse(html, Parser.xmlParser());

        return new XDescription(
                parseTitle(description),
                parseVideo(description),
                parseImages(description)
        );
    }

    private String parseTitle(Document description) {
        var title = description.selectFirst("p");

        return title != null ? title.text() : null;
    }

    private boolean parseVideo(Document description) {
        return description.selectFirst("br:containsOwn(Video)") != null;
    }

    private List<String> parseImages(Document description) {
        return description.select("img")
                .stream()
                .map(img -> img.attr("src"))
                .toList();
    }
}
