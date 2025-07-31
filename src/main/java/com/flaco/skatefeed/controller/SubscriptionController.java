package com.flaco.skatefeed.controller;

import com.flaco.skatefeed.dto.SubscriptionRequest;
import com.flaco.skatefeed.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions") // Tutte le richieste a questo controller inizieranno con /api/subscriptions
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<String> subscribe(@RequestBody SubscriptionRequest request) {
        subscriptionService.subscribe(request);

        return ResponseEntity.ok("Iscrizione elaborata con successo!");
    }
}