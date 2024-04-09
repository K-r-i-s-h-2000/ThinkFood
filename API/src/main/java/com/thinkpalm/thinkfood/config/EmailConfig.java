package com.thinkpalm.thinkfood.config;

import jakarta.mail.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public Session emailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator());
    }

    private class Authenticator extends jakarta.mail.Authenticator {
        protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
            return new jakarta.mail.PasswordAuthentication("thinkfood765@gmail.com", "fgcj fzcv wyiv gfzm");
        }
    }
}

