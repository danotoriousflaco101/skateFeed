package com.flaco.skatefeed.config;

import com.flaco.skatefeed.integration.ArticleProcessor;
import com.flaco.skatefeed.integration.ArticleTransformer;
import com.flaco.skatefeed.integration.EmailPreparer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Value("${feed.berrics.url}")
    private Resource berricsUrl;

    @Value("${feed.thrasher.url}")
    private Resource thrasherUrl;

    @Bean
    public IntegrationFlow berricsFeedFlow(MetadataStore metadataStore) {
        return IntegrationFlow.from(Feed.inboundAdapter(berricsUrl, "berricsFeed")
                                .metadataStore(metadataStore),
                        e -> e.poller(p -> p.fixedRate(3600000))) // 1 ora
                .channel(articlesChannel())
                .get();
    }

    @Bean
    public MessageChannel articlesChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow articleProcessingFlow(ArticleTransformer articleTransformer, ArticleProcessor articleProcessor) {
        return IntegrationFlow.from(articlesChannel())
                .transform(articleTransformer)              // 1. Trasforma l'articolo grezzo
                .handle(articleProcessor, "processArticle") // 2. Lo passa al processore intelligente
                .filter(payload -> payload != null)          // 3. Scarta i messaggi null (duplicati o non interessanti)
                .channel(notificationsChannel())             // 4. Invia i pacchetti pronti al canale delle notifiche
                .get();
    }

    @Bean
    public MessageChannel notificationsChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow emailSendingFlow(EmailPreparer emailPreparer, JavaMailSender javaMailSender) {
        return IntegrationFlow.from(notificationsChannel())
                .handle(emailPreparer, "prepareEmail")
                .log(LoggingHandler.Level.INFO, "Sending email")
                .handle(Mail.outboundAdapter(javaMailSender))
                .get();
    }

    @Bean
    public MetadataStore metadataStore() {
        PropertiesPersistingMetadataStore metadataStore = new PropertiesPersistingMetadataStore();
        metadataStore.setBaseDirectory("build/meta-store");
        return metadataStore;
    }
}