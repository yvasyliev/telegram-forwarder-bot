package io.github.yvasyliev.telegramforwarderbot.reddit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.yvasyliev.telegramforwarderbot.reddit.databind.util.PermalinkDeserializerConverter;
import io.github.yvasyliev.telegramforwarderbot.reddit.deser.std.EditedDeserializer;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public record Link(
        @JsonProperty("approved_at_utc") String approvedAtUtc,
        @JsonProperty("subreddit") String subreddit,
        @JsonProperty("selftext") String selftext,
        @JsonProperty("author_fullname") String authorFullname,
        @JsonProperty("saved") Boolean saved,
        @JsonProperty("mod_reason_title") String modReasonTitle,
        @JsonProperty("gilded") Integer gilded,
        @JsonProperty("clicked") Boolean clicked,
        @JsonProperty("title") String title,
        @JsonProperty("link_flair_richtext") List<FlairRichtext> linkFlairRichtext,
        @JsonProperty("subreddit_name_prefixed") String subredditNamePrefixed,
        @JsonProperty("hidden") Boolean hidden,
        @JsonProperty("pwls") Integer pwls,
        @JsonProperty("link_flair_css_class") String linkFlairCssClass,
        @JsonProperty("downs") Integer downs,
        @JsonProperty("thumbnail_height") Integer thumbnailHeight,
        @JsonProperty("top_awarded_type") String topAwardedType,
        @JsonProperty("hide_score") Boolean hideScore,
        @JsonProperty("media_metadata") Map<String, Metadata> mediaMetadata,
        @JsonProperty("name") String name,
        @JsonProperty("quarantine") Boolean quarantine,
        @JsonProperty("link_flair_text_color") String linkFlairTextColor,
        @JsonProperty("upvote_ratio") Double upvoteRatio,
        @JsonProperty("author_flair_background_color") String authorFlairBackgroundColor,
        @JsonProperty("subreddit_type") String subredditType,
        @JsonProperty("ups") Integer ups,
        @JsonProperty("total_awards_received") Integer totalAwardsReceived,
        @JsonProperty("media_embed") MediaEmbed mediaEmbed,
        @JsonProperty("thumbnail_width") Integer thumbnailWidth,
        @JsonProperty("author_flair_template_id") String authorFlairTemplateId,
        @JsonProperty("is_original_content") Boolean isOriginalContent,
        @JsonProperty("user_reports") ArrayNode userReports,
        @JsonProperty("secure_media") Media secureMedia,
        @JsonProperty("is_reddit_media_domain") Boolean isRedditMediaDomain,
        @JsonProperty("is_meta") Boolean isMeta,
        @JsonProperty("category") String category,
        @JsonProperty("secure_media_embed") MediaEmbed secureMediaEmbed,
        @JsonProperty("gallery_data") GalleryData galleryData,
        @JsonProperty("link_flair_text") String linkFlairText,
        @JsonProperty("can_mod_post") Boolean canModPost,
        @JsonProperty("score") Integer score,
        @JsonProperty("approved_by") String approvedBy,
        @JsonProperty("is_created_from_ads_ui") Boolean isCreatedFromAdsUi,
        @JsonProperty("author_premium") Boolean authorPremium,
        @JsonProperty("thumbnail") String thumbnail,
        @JsonProperty("edited") @JsonDeserialize(using = EditedDeserializer.class) Instant edited,
        @JsonProperty("author_flair_css_class") String authorFlairCssClass,
        @JsonProperty("author_flair_richtext") List<FlairRichtext> authorFlairRichtext,
        @JsonProperty("gildings") Map<String, Integer> gildings,
        @JsonProperty("post_hint") PostHint postHint,
        @JsonProperty("content_categories") List<String> contentCategories,
        @JsonProperty("is_self") Boolean isSelf,
        @JsonProperty("mod_note") String modNote,
        @JsonProperty("crosspost_parent_list") List<Link> crosspostParentList,
        @JsonProperty("created") Instant created,
        @JsonProperty("link_flair_type") String linkFlairType,
        @JsonProperty("wls") Integer wls,
        @JsonProperty("removed_by_category") String removedByCategory,
        @JsonProperty("banned_by") String bannedBy,
        @JsonProperty("author_flair_type") String authorFlairType,
        @JsonProperty("domain") String domain,
        @JsonProperty("allow_live_comments") Boolean allowLiveComments,
        @JsonProperty("selftext_html") String selftextHtml,
        @JsonProperty("likes") Boolean likes,
        @JsonProperty("suggested_sort") String suggestedSort,
        @JsonProperty("banned_at_utc") Instant bannedAtUtc,
        @JsonProperty("url_overridden_by_dest") URL urlOverriddenByDest,
        @JsonProperty("view_count") Integer viewCount,
        @JsonProperty("archived") Boolean archived,
        @JsonProperty("no_follow") Boolean noFollow,
        @JsonProperty("is_crosspostable") Boolean isCrosspostable,
        @JsonProperty("pinned") Boolean pinned,
        @JsonProperty("over_18") Boolean over18,
        @JsonProperty("preview") Preview preview,
        @JsonProperty("all_awardings") List<Awarding> allAwardings,
        @JsonProperty("awarders") ArrayNode awarders,
        @JsonProperty("media_only") Boolean mediaOnly,
        @JsonProperty("can_gild") Boolean canGild,
        @JsonProperty("spoiler") Boolean spoiler,
        @JsonProperty("locked") Boolean locked,
        @JsonProperty("author_flair_text") String authorFlairText,
        @JsonProperty("treatment_tags") ArrayNode treatmentTags,
        @JsonProperty("visited") Boolean visited,
        @JsonProperty("removed_by") String removedBy,
        @JsonProperty("num_reports") Integer numReports,
        @JsonProperty("distinguished") String distinguished,
        @JsonProperty("subreddit_id") String subredditId,
        @JsonProperty("author_is_blocked") Boolean authorIsBlocked,
        @JsonProperty("mod_reason_by") String modReasonBy,
        @JsonProperty("removal_reason") String removalReason,
        @JsonProperty("link_flair_background_color") String linkFlairBackgroundColor,
        @JsonProperty("id") String id,
        @JsonProperty("is_robot_indexable") Boolean isRobotIndexable,
        @JsonProperty("report_reasons") List<String> reportReasons,
        @JsonProperty("author") String author,
        @JsonProperty("discussion_type") String discussionType,
        @JsonProperty("num_comments") Integer numComments,
        @JsonProperty("send_replies") Boolean sendReplies,
        @JsonProperty("whitelist_status") String whitelistStatus,
        @JsonProperty("contest_mode") Boolean contestMode,
        @JsonProperty("mod_reports") ArrayNode modReports,
        @JsonProperty("author_patreon_flair") Boolean authorPatreonFlair,
        @JsonProperty("author_flair_text_color") String authorFlairTextColor,
        @JsonProperty("permalink") @JsonDeserialize(converter = PermalinkDeserializerConverter.class) URL permalink,
        @JsonProperty("parent_whitelist_status") String parentWhitelistStatus,
        @JsonProperty("stickied") Boolean stickied,
        @JsonProperty("url") URL url,
        @JsonProperty("subreddit_subscribers") Integer subredditSubscribers,
        @JsonProperty("created_utc") Instant createdUtc,
        @JsonProperty("num_crossposts") Integer numCrossposts,
        @JsonProperty("media") Media media,
        @JsonProperty("is_video") Boolean isVideo
) implements Comparable<Link> {
    public boolean isNsfw() {
        return "nsfw".equals(thumbnail);
    }

    public boolean hasGalleryData() {
        return galleryData != null;
    }

    public boolean hasPostHint() {
        return postHint != null;
    }

    @Override
    public int compareTo(@NotNull Link o) {
        return created.compareTo(o.created);
    }

    public enum PostHint {
        @JsonProperty("hosted:video") HOSTED_VIDEO,
        @JsonProperty("image") IMAGE,
        @JsonProperty("link") LINK,
        @JsonProperty("rich:video") RICH_VIDEO,
        @JsonProperty("gallery") GALLERY
    }

    public record Preview(
            @JsonProperty("images") List<Image> images,
            @JsonProperty("enabled") Boolean enabled,
            @JsonProperty("reddit_video_preview") RedditVideo redditVideoPreview
    ) {
        public record Image(
                @JsonProperty("source") Resolution source,
                @JsonProperty("resolutions") List<Resolution> resolutions,
                @JsonProperty("variants") Variants variants,
                @JsonProperty("id") String id
        ) {}
    }

    public record Resolution(
            @JsonProperty("url") @JsonAlias("u") URL url,
            @JsonProperty("width") @JsonAlias("x") Integer width,
            @JsonProperty("height") @JsonAlias("y") Integer height,
            @JsonProperty("gif") URL gif,
            @JsonProperty("mp4") URL mp4
    ) implements Comparable<Resolution> {
        @Override
        public int compareTo(Resolution resolution) {
            return Integer.compare(width * height, resolution.width * resolution.height);
        }
    }

    public record Variants(
            @JsonProperty("gif") Variant gif,
            @JsonProperty("mp3") Variant mp3,
            @JsonProperty("mp4") Variant mp4,
            @JsonProperty("nsfw") Variant nsfw,
            @JsonProperty("obfuscated") Variant obfuscated
    ) {
        public boolean hasGif() {
            return gif != null;
        }
    }

    public enum VariantType {
        @JsonProperty("gif") GIF,
        @JsonProperty("mp3") MP3,
        @JsonProperty("mp4") MP4,
        @JsonProperty("nsfw") NSFW,
        @JsonProperty("obfuscated") OBFUSCATED
    }

    public record Variant(
            @JsonProperty("source") Resolution source,
            @JsonProperty("resolutions") List<Resolution> resolutions
    ) {}

    public record GalleryData(@JsonProperty("items") List<Item> items) {
        public record Item(@JsonProperty("media_id") String mediaId, @JsonProperty("id") Integer id) {}
    }

    public record Metadata(
            @JsonProperty("status") Status status,
            @JsonProperty("e") Type type,
            @JsonProperty("m") Format format,
            @JsonProperty("p") List<Resolution> resolutions,
            @JsonProperty("s") Resolution source,
            @JsonProperty("id") String id
    ) {
        public enum Status {
            @JsonProperty("valid") VALID
        }

        public enum Type {
            @JsonProperty("Image") IMAGE,
            @JsonProperty("AnimatedImage") ANIMATED_IMAGE
        }

        public enum Format {
            @JsonProperty("image/gif") IMAGE_GIF,
            @JsonProperty("image/jpg") IMAGE_JPG,
            @JsonProperty("image/png") IMAGE_PNG
        }
    }

    public record PollData(
            @JsonProperty("prediction_status") String predictionStatus,
            @JsonProperty("tournament_id") String tournamentId,
            @JsonProperty("voting_end_timestamp") Instant votingEndTimestamp,
            @JsonProperty("total_vote_count") Integer totalVoteCount,
            @JsonProperty("vote_updates_remained") Integer voteUpdatesRemained,
            @JsonProperty("is_prediction") Boolean isPrediction,
            @JsonProperty("resolved_option_id") String resolvedOptionId,
            @JsonProperty("user_won_amount") Double userWonAmount,
            @JsonProperty("user_selection") String userSelection,
            @JsonProperty("options") List<Option> options,
            @JsonProperty("total_stake_amount") Double totalStakeAmount
    ) {
        public record Option(@JsonProperty("text") String text, @JsonProperty("id") String id) {}
    }

    public record Media(@JsonProperty("reddit_video") RedditVideo redditVideo) {}

    public record RedditVideo(
            @JsonProperty("bitrate_kbps") Integer bitrateKbps,
            @JsonProperty("fallback_url") URL fallbackUrl,
            @JsonProperty("has_audio") Boolean hasAudio,
            @JsonProperty("height") Integer height,
            @JsonProperty("width") Integer width,
            @JsonProperty("scrubber_media_url") URL scrubberMediaUrl,
            @JsonProperty("dash_url") URL dashUrl,
            @JsonProperty("duration") Duration duration,
            @JsonProperty("hls_url") URL hlsUrl,
            @JsonProperty("is_gif") Boolean isGif,
            @JsonProperty("transcoding_status") TranscodingStatus transcodingStatus
    ) {
        public enum TranscodingStatus {
            @JsonProperty("completed") COMPLETED
        }
    }

    public record FlairRichtext(
            @JsonProperty("a") String emoji,
            @JsonProperty("u") URL url,
            @JsonProperty("t") String type
    ) {
    }

    public record MediaEmbed(
            @JsonProperty("content") String content,
            @JsonProperty("height") Integer height,
            @JsonProperty("scrolling") Boolean scrolling,
            @JsonProperty("width") Integer width
    ) {}

    public record Awarding(
            @JsonProperty("giver_coin_reward") Integer giverCoinReward,
            @JsonProperty("subreddit_id") String subredditId,
            @JsonProperty("is_new") Boolean isNew,
            @JsonProperty("days_of_drip_extension") Integer daysOfDripExtension,
            @JsonProperty("coin_price") Integer coinPrice,
            @JsonProperty("id") String id,
            @JsonProperty("penny_donate") Integer pennyDonate,
            @JsonProperty("coin_reward") Integer coinReward,
            @JsonProperty("icon_url") URL iconUrl,
            @JsonProperty("days_of_premium") Integer daysOfPremium,
            @JsonProperty("icon_height") Integer iconHeight,
            @JsonProperty("tiers_by_required_awardings") JsonNode tiersByRequiredAwardings,
            @JsonProperty("icon_width") Integer iconWidth,
            @JsonProperty("static_icon_width") Integer staticIconWidth,
            @JsonProperty("start_date") Instant startDate,
            @JsonProperty("is_enabled") Boolean isEnabled,
            @JsonProperty("awardings_required_to_grant_benefits") Integer awardingsRequiredToGrantBenefits,
            @JsonProperty("description") String description,
            @JsonProperty("end_date") Instant endDate,
            @JsonProperty("subreddit_coin_reward") Integer subredditCoinReward,
            @JsonProperty("count") Integer count,
            @JsonProperty("static_icon_height") Integer staticIconHeight,
            @JsonProperty("name") String name,
            @JsonProperty("icon_format") String iconFormat,
            @JsonProperty("award_sub_type") String awardSubType,
            @JsonProperty("penny_price") Integer pennyPrice,
            @JsonProperty("award_type") String awardType,
            @JsonProperty("static_icon_url") URL staticIconUrl
    ) {}
}