package io.github.yvasyliev.telegramforwarderbot.reddit.util;

import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import lombok.experimental.UtilityClass;

import java.net.URL;
import java.util.Collections;

@UtilityClass
public class PhotoUtils {
    public URL getUrl(Link.Metadata metadata, int maxPhotoDimensions) {
        var resolution = metadata.source();

        if (resolution.width() + resolution.height() > maxPhotoDimensions) {
            resolution = Collections.max(metadata.resolutions());
        }

        return resolution.url();
    }
}
