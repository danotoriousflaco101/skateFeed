package com.flaco.skatefeed.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "processed_articles")
public class ProcessedArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_url", unique = true, nullable = false, length = 1024)
    private String articleUrl;

    @CreationTimestamp
    @Column(name = "processed_at", updatable = false)
    private LocalDateTime processedAt;

    public ProcessedArticle(String articleUrl) {
        this.articleUrl = articleUrl;
    }
}