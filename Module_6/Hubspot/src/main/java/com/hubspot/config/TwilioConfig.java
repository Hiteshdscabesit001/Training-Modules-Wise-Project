package com.hubspot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Configuration
@Component
@ConfigurationProperties(prefix = "twilio")
public class TwilioConfig {
    private String accountSid;
    private String authToken;
    private String phoneNumber;


    public TwilioConfig(String accountSid, String authToken, String phoneNumber) {
        super();
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.phoneNumber = phoneNumber;
    }
    public TwilioConfig() {
        super();
        // TODO Auto-generated constructor stub
    }



}