package io.github.yvasyliev.forwarder.telegram.x.configuration;

import io.github.yvasyliev.forwarder.telegram.core.dto.SendMediaGroupDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendPhotoDTO;
import io.github.yvasyliev.forwarder.telegram.core.dto.SendVideoDTO;
import io.github.yvasyliev.forwarder.telegram.core.service.PostSender;
import io.github.yvasyliev.forwarder.telegram.core.util.CloseableSupplier;
import io.github.yvasyliev.forwarder.telegram.x.mapper.XSendMediaGroupDTOMapper;
import io.github.yvasyliev.forwarder.telegram.x.mapper.XSendPhotoDTOMapper;
import io.github.yvasyliev.forwarder.telegram.x.mapper.XSendVideoDTOMapper;
import io.github.yvasyliev.forwarder.telegram.x.service.sender.XPhotoPostSenderStrategy;
import io.github.yvasyliev.forwarder.telegram.x.service.sender.XPostSenderStrategy;
import io.github.yvasyliev.forwarder.telegram.x.service.sender.XSendMediaGroupDTOStrategy;
import io.github.yvasyliev.forwarder.telegram.x.service.sender.XVideoPostSenderStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

/**
 * Configuration class for X post sender strategies.
 */
@Configuration
public class XPostSenderConfiguration {
    /**
     * Creates a bean for sending photo posts to Telegram.
     *
     * @param sendPhotoDTOMapper the mapper for converting X posts to {@link SendPhotoDTO}
     * @param photoSender        the sender for sending photo posts to Telegram
     * @return a new instance of {@link XPhotoPostSenderStrategy}
     */
    @Bean
    @ConditionalOnMissingBean(name = "xPhotoPostSenderStrategy")
    public XPostSenderStrategy xPhotoPostSenderStrategy(
            XSendPhotoDTOMapper sendPhotoDTOMapper,
            PostSender<CloseableSupplier<SendPhotoDTO>, Message> photoSender
    ) {
        return new XPhotoPostSenderStrategy(sendPhotoDTOMapper, photoSender);
    }

    /**
     * Creates a bean for sending media group posts to Telegram.
     *
     * @param sendMediaGroupDTOMapper the mapper for converting X posts to {@link SendMediaGroupDTO}
     * @param mediaGroupSender        the sender for sending media group posts to Telegram
     * @return a new instance of {@link XSendMediaGroupDTOStrategy}
     */
    @Bean
    @ConditionalOnMissingBean(name = "xSendMediaGroupDTOStrategy")
    public XPostSenderStrategy xSendMediaGroupDTOStrategy(
            XSendMediaGroupDTOMapper sendMediaGroupDTOMapper,
            PostSender<CloseableSupplier<SendMediaGroupDTO>, List<Message>> mediaGroupSender
    ) {
        return new XSendMediaGroupDTOStrategy(sendMediaGroupDTOMapper, mediaGroupSender);
    }

    /**
     * Creates a bean for sending video posts to Telegram.
     *
     * @param sendVideoDTOMapper the mapper for converting X posts to {@link SendVideoDTO}
     * @param videoSender        the sender for sending video posts to Telegram
     * @return a new instance of {@link XVideoPostSenderStrategy}
     */
    @Bean
    @ConditionalOnMissingBean(name = "xVideoPostSenderStrategy")
    public XPostSenderStrategy xVideoPostSenderStrategy(
            XSendVideoDTOMapper sendVideoDTOMapper,
            PostSender<CloseableSupplier<SendVideoDTO>, Message> videoSender
    ) {
        return new XVideoPostSenderStrategy(sendVideoDTOMapper, videoSender);
    }
}
