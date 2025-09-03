package io.github.yvasyliev.telegramforwarder.reddit.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yvasyliev.telegramforwarder.core.configuration.TelegramMediaProperties;
import io.github.yvasyliev.telegramforwarder.core.dto.InputFileDTO;
import io.github.yvasyliev.telegramforwarder.core.dto.InputMediaDTO;
import io.github.yvasyliev.telegramforwarder.core.service.PostForwarderManager;
import io.github.yvasyliev.telegramforwarder.core.service.PostSender;
import io.github.yvasyliev.telegramforwarder.reddit.repository.RedditInstantPropertyRepository;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditInstantPropertyService;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditPostForwarderManager;
import io.github.yvasyliev.telegramforwarder.reddit.service.RedditService;
import io.github.yvasyliev.telegramforwarder.reddit.service.VideoDownloader;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.AnimationMetadataForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.Forwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.ImageAnimationForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.LinkForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.MediaGroupForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.MetadataForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.PhotoForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.PhotoMetadataForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.VideoAnimationForwarder;
import io.github.yvasyliev.telegramforwarder.reddit.service.forwarder.VideoForwarder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * Configuration class for setting up the Reddit service client.
 *
 * <p>
 * This configuration uses OAuth2 for authentication and sets up a {@link RedditService} bean
 * that can be used to interact with the Reddit API.
 */
@AutoConfiguration
@EnableConfigurationProperties({
        OAuth2ClientProperties.class,
        RedditProperties.class
})
@EnableJpaRepositories("io.github.yvasyliev.telegramforwarder.reddit.repository")
@EntityScan("io.github.yvasyliev.telegramforwarder.reddit.entity")
public class RedditAutoConfiguration {
    /**
     * Creates a {@link RedditService} bean configured with OAuth2 client properties and
     * a custom HTTP request interceptor.
     *
     * @param oAuth2ClientProperties the OAuth2 client properties
     * @param redditProperties       the Reddit properties containing API host and user agent
     * @param objectMapper           the Jackson ObjectMapper for JSON serialization/deserialization
     * @return a configured {@link RedditService} instance
     */
    @Bean
    @ConditionalOnMissingBean
    public RedditService redditService(
            OAuth2ClientProperties oAuth2ClientProperties,
            RedditProperties redditProperties,
            ObjectMapper objectMapper
    ) {
        var clientRegistrations = List.copyOf(new OAuth2ClientPropertiesMapper(oAuth2ClientProperties)
                .asClientRegistrations()
                .values()
        );
        var clientRegistrationRepository = new InMemoryClientRegistrationRepository(clientRegistrations);
        var authorizedClientService = new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
        var authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientService
        );
        var oAuth2ClientHttpRequestInterceptor = new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);
        oAuth2ClientHttpRequestInterceptor.setClientRegistrationIdResolver(request -> "reddit");

        var restClient = RestClient.builder()
                .baseUrl(redditProperties.apiHost())
                .defaultHeader(HttpHeaders.USER_AGENT, redditProperties.userAgent())
                .requestInterceptor(oAuth2ClientHttpRequestInterceptor)
                .messageConverters(converters -> Objects.requireNonNull(CollectionUtils.findValueOfType(
                                converters,
                                AbstractJackson2HttpMessageConverter.class
                        )).setObjectMapper(objectMapper)
                )
                .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(RedditService.class);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditAnimationMetadataForwarder")
    public MetadataForwarder redditAnimationMetadataForwarder(PostSender<InputFileDTO, Message> animationSender) {
        return new AnimationMetadataForwarder(animationSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditPhotoMetadataForwarder")
    public MetadataForwarder redditPhotoMetadataForwarder(
            TelegramMediaProperties mediaProperties,
            PostSender<InputFileDTO, Message> photoSender
    ) {
        return new PhotoMetadataForwarder(mediaProperties, photoSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditImageAnimationForwarder")
    public Forwarder redditImageAnimationForwarder(PostSender<InputFileDTO, Message> animationSender) {
        return new ImageAnimationForwarder(animationSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditLinkForwarder")
    public Forwarder redditLinkForwarder(PostSender<URL, Message> urlSender) {
        return new LinkForwarder(urlSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditMediaGroupForwarder")
    public Forwarder redditMediaGroupForwarder(
            TelegramMediaProperties mediaProperties,
            MetadataForwarder redditAnimationMetadataForwarder,
            MetadataForwarder redditPhotoMetadataForwarder,
            PostSender<List<InputMediaDTO>, List<Message>> mediaGroupSender
    ) {
        return new MediaGroupForwarder(
                mediaProperties,
                redditAnimationMetadataForwarder,
                redditPhotoMetadataForwarder,
                mediaGroupSender
        );
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditPhotoForwarder")
    public Forwarder redditPhotoForwarder(PostSender<InputFileDTO, Message> photoSender) {
        return new PhotoForwarder(photoSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditVideoAnimationForwarder")
    public Forwarder redditVideoAnimationForwarder(PostSender<InputFileDTO, Message> animationSender) {
        return new VideoAnimationForwarder(animationSender);
    }

    @Bean
    @ConditionalOnMissingBean(name = " redditVideoForwarder")
    public Forwarder redditVideoForwarder(
            PostSender<InputFileDTO, Message> videoSender,
            VideoDownloader videoDownloader
    ) {
        return new VideoForwarder(videoSender, videoDownloader);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedditInstantPropertyService redditInstantPropertyService(RedditInstantPropertyRepository repository) {
        return new RedditInstantPropertyService(repository);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redditPostForwarderManager")
    public PostForwarderManager redditPostForwarderManager(
            RedditInstantPropertyService instantPropertyService,
            RedditService redditService,
            RedditProperties redditProperties,
            Forwarder redditMediaGroupForwarder,
            Forwarder redditVideoForwarder,
            Forwarder redditImageAnimationForwarder,
            Forwarder redditPhotoForwarder,
            Forwarder redditLinkForwarder,
            Forwarder redditVideoAnimationForwarder
    ) {
        return new RedditPostForwarderManager(
                instantPropertyService,
                redditService,
                redditProperties,
                redditMediaGroupForwarder,
                redditVideoForwarder,
                redditImageAnimationForwarder,
                redditPhotoForwarder,
                redditLinkForwarder,
                redditVideoAnimationForwarder
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public VideoDownloader videoDownloader(RedditProperties redditProperties) {
        return new VideoDownloader(redditProperties);
    }
}
