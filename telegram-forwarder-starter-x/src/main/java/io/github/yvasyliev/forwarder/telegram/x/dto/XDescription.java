package io.github.yvasyliev.forwarder.telegram.x.dto;

import java.util.List;

/**
 * Description of an X post.
 *
 * @param title   post title
 * @param isVideo whether the post is a video
 * @param images  list of image URLs in the post
 */
public record XDescription(String title, boolean isVideo, List<String> images) {}
