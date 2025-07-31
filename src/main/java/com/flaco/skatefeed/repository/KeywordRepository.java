package com.flaco.skatefeed.repository;

import com.flaco.skatefeed.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("SELECT DISTINCT lower(k.value) FROM Keyword k")
    List<String> findDistinctKeywords();

    List<Keyword> findByValueInIgnoreCase(Collection<String> values);
}