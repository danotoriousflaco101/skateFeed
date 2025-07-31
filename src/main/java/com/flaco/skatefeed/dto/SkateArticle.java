package com.flaco.skatefeed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkateArticle {
    private String title;
    private String link;
    private String content;
    private String source;
}