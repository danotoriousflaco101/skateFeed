package com.flaco.skatefeed.integration;

import com.flaco.skatefeed.dto.NotificationPayload;
import org.springframework.integration.mail.MailHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EmailPreparer {

    public Message<String> prepareEmail(NotificationPayload payload) {
        String subject = "SkateFeed: Nuova news su " + payload.getArticle().getSource();
        String body = buildEmailBody(payload);
        String recipients = payload.getRecipientEmails().stream().collect(Collectors.joining(","));

        return MessageBuilder.withPayload(body)
                .setHeader(MailHeaders.TO, recipients)
                .setHeader(MailHeaders.SUBJECT, subject)
                .setHeader("Content-Type", "text/html; charset=UTF-8") // Impostiamo il contenuto come HTML
                .build();
    }


    private String buildEmailBody(NotificationPayload payload) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<h1>SkateFeed Notizia</h1>");
        sb.append("<h2>").append(payload.getArticle().getTitle()).append("</h2>");
        sb.append("<p><i>Fonte: ").append(payload.getArticle().getSource()).append("</i></p>");
        sb.append("<hr>");
        sb.append(payload.getArticle().getContent());
        sb.append("<br><br>");
        sb.append("<a href=\"").append(payload.getArticle().getLink()).append("\">Leggi l'articolo completo</a>");
        sb.append("<hr>");
        sb.append("<p><small>Hai ricevuto questa email perché sei iscritto a SkateFeed.</small></p>");
        sb.append("</body></html>");
        return sb.toString();
    }
}