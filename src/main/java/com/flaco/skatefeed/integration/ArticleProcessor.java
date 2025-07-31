package com.flaco.skatefeed.integration;

import com.flaco.skatefeed.dto.NotificationPayload;
import com.flaco.skatefeed.dto.SkateArticle;
import com.flaco.skatefeed.model.Keyword;
import com.flaco.skatefeed.model.ProcessedArticle;
import com.flaco.skatefeed.repository.KeywordRepository;
import com.flaco.skatefeed.repository.ProcessedArticleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ArticleProcessor {

    private final ProcessedArticleRepository processedArticleRepo;
    private final KeywordRepository keywordRepository;

    public ArticleProcessor(ProcessedArticleRepository processedArticleRepo, KeywordRepository keywordRepository) {
        this.processedArticleRepo = processedArticleRepo;
        this.keywordRepository = keywordRepository;
    }

    @Transactional
    public NotificationPayload processArticle(SkateArticle article) {
        if (processedArticleRepo.existsByArticleUrl(article.getLink())) {
            return null;
        }

        List<String> allKeywords = keywordRepository.findDistinctKeywords();
        String articleText = (article.getTitle() + " " + article.getContent()).toLowerCase();

        List<String> matchedKeywords = allKeywords.stream()
                .filter(articleText::contains)
                .toList();

        if (matchedKeywords.isEmpty()) {
            processedArticleRepo.save(new ProcessedArticle(article.getLink()));
            return null;
        }

        List<Keyword> keywordsWithUsers = keywordRepository.findByValueInIgnoreCase(matchedKeywords);
        Set<String> recipientEmails = keywordsWithUsers.stream()
                .map(keyword -> keyword.getUser().getEmail())
                .collect(Collectors.toSet());


        if (recipientEmails.isEmpty()) {
            processedArticleRepo.save(new ProcessedArticle(article.getLink()));
            return null;
        }

        processedArticleRepo.save(new ProcessedArticle(article.getLink()));
        return new NotificationPayload(article, recipientEmails);
    }
}