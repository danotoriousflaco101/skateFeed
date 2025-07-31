package com.flaco.skatefeed.repository;

import com.flaco.skatefeed.model.ProcessedArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedArticleRepository extends JpaRepository<ProcessedArticle, Long> {

    boolean existsByArticleUrl(String articleUrl);
}
