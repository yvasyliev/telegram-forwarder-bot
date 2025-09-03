package io.github.yvasyliev.telegramforwarder.reddit.util;

import io.github.yvasyliev.telegramforwarder.reddit.dto.Link;
import lombok.experimental.UtilityClass;

import java.net.URL;
import java.util.Collections;

/**
 * Utility class for handling photo URLs from Reddit links.
 *
 * <p>
 * Provides methods to retrieve the appropriate URL based on the metadata and maximum photo dimensions.
 */
@UtilityClass
public class PhotoUtils {
    /**
     * Retrieves the URL for a photo based on the provided metadata and maximum dimensions.
     *
     * <p>
     * If the combined width and height of the source resolution exceeds the maximum dimensions,
     * it selects the largest available resolution.
     *
     * @param metadata           The link metadata containing photo resolutions.
     * @param maxPhotoDimensions The maximum allowed dimensions for the photo.
     * @return The URL of the selected photo resolution.
     */
    public URL getUrl(Link.Metadata metadata, int maxPhotoDimensions) {
        var resolution = metadata.source();

        if (resolution.width() + resolution.height() > maxPhotoDimensions) {
            resolution = Collections.max(metadata.resolutions());
        }

        return resolution.url();
    }
}
