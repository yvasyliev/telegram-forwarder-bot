package io.github.yvasyliev.telegramforwarderbot.reddit.service.forwarder;

import io.github.yvasyliev.telegramforwarderbot.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarderbot.reddit.dto.Link;
import io.github.yvasyliev.telegramforwarderbot.reddit.service.VideoDownloader;
import io.github.yvasyliev.telegramforwarderbot.service.sender.VideoSender;
import io.github.yvasyliev.telegramforwarderbot.util.InputStreamSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VideoForwarderTest {
    @InjectMocks private VideoForwarder forwarder;
    @Mock private VideoSender sender;
    @Mock private VideoDownloader downloader;

    @Test
    void testForward() throws TelegramApiException, IOException {
        var link = mock(Link.class);
        var url = mock(URL.class);
        var id = "testId";
        var isNsfw = true;
        var title = "testTitle";
        var video = new InputFileDTO(mock(InputStreamSupplier.class), id + ".mp4", isNsfw);

        when(downloader.getVideoDownloadUrl(link)).thenReturn(url);
        when(link.id()).thenReturn(id);
        when(link.isNsfw()).thenReturn(isNsfw);
        when(link.title()).thenReturn(title);

        forwarder.forward(link);

        verify(sender).sendVideo(refEq(video, "fileSupplier"), eq(title));
    }
}
