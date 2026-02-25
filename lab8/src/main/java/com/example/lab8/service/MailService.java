
package com.example.lab8.service;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import lombok.Builder.Default;

public interface MailService {

    @Data
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class Mail {
        @Default
        String from = "WebShop <web-shop@gmail.com>";
        String to;
        String cc;
        String bcc;
        String subject;
        String body;
        String filenames; // danh sách file, phân cách bởi dấu phẩy hoặc chấm phẩy
    }

    void send(Mail mail);

    default void send(String to, String subject, String body) {
        Mail mail = Mail.builder()
                .to(to)
                .subject(subject)
                .body(body)
                .build();
        send(mail);
    }

    void push(Mail mail);

    default void push(String to, String subject, String body) {
        push(Mail.builder().to(to).subject(subject).body(body).build());
    }
}