package com.flaco.skatefeed.service;

import com.flaco.skatefeed.dto.SubscriptionRequest;
import com.flaco.skatefeed.model.Keyword;
import com.flaco.skatefeed.model.User;
import com.flaco.skatefeed.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public SubscriptionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void subscribe(SubscriptionRequest subscriptionRequest) {
        if (!StringUtils.hasText(subscriptionRequest.getEmail())) {
            throw new IllegalArgumentException("L'email non può essere vuota.");
        }

        log.info("Processing subscription for email: {}", subscriptionRequest.getEmail());

        User user = userRepository.findByEmail(subscriptionRequest.getEmail())
                .orElse(new User());
        user.setEmail(subscriptionRequest.getEmail());

        Set<String> newKeywordValues = (subscriptionRequest.getKeywords() == null)
                ? Collections.emptySet()
                : subscriptionRequest.getKeywords().stream()
                .filter(StringUtils::hasText)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        Set<Keyword> existingKeywords = new HashSet<>(user.getKeywords());

        existingKeywords.stream()
                .filter(existingKeyword -> !newKeywordValues.contains(existingKeyword.getValue()))
                .forEach(keywordToRemove -> {
                    log.info("Removing keyword '{}' for user {}", keywordToRemove.getValue(), user.getEmail());
                    user.removeKeyword(keywordToRemove);
                });

        newKeywordValues.stream()
                .filter(newKeywordValue ->
                        user.getKeywords().stream().noneMatch(kw -> kw.getValue().equals(newKeywordValue))
                )
                .forEach(keywordValueToAdd -> {
                    log.info("Adding keyword '{}' for user {}", keywordValueToAdd, user.getEmail());
                    user.addKeyword(new Keyword(keywordValueToAdd, user));
                });


        userRepository.save(user);

        log.info("Subscription for {} processed successfully.", user.getEmail());
    }
}