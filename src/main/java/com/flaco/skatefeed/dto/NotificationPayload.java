package com.flaco.skatefeed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class NotificationPayload {

    private SkateArticle article;
    private Set<String> recipientEmails;

}
