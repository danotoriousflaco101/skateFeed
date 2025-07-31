package com.flaco.skatefeed.integration;

import com.flaco.skatefeed.dto.SkateArticle;
import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component // Dichiara a Spring che questo è un componente da gestire
public class ArticleTransformer {

    @Transformer
    public SkateArticle transformSyndEntryToSkateArticle(Message<SyndEntry> message) {
        SyndEntry entry = message.getPayload();
        String sourceId = (String) message.getHeaders().get("feed_id");

        String title = entry.getTitle();
        String link = entry.getLink();
        String content = entry.getDescription() != null ? entry.getDescription().getValue() : "";

        String prettySource = "Unknown Source";
        if ("berricsFeed".equals(sourceId)) {
            prettySource = "The Berrics";
        } else if ("thrasherFeed".equals(sourceId)) {
            prettySource = "Thrasher Magazine";
        }

        return new SkateArticle(title, link, content, prettySource);
    }
}