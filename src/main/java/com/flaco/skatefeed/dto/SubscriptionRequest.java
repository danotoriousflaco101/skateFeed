package com.flaco.skatefeed.dto;

import lombok.Data;
import java.util.List;

@Data
public class SubscriptionRequest {

    private String email;
    private List<String> keywords;
}
