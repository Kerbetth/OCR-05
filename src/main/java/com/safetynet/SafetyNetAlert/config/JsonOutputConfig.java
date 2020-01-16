package com.safetynet.SafetyNetAlert.config;

import com.safetynet.SafetyNetAlert.services.JsonOutputFactory;
import com.safetynet.SafetyNetAlert.services.JsonOutputService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class JsonOutputConfig {


/*
    @Bean
    public JsonOutputFactory jsonOutputFactory(){
        return new JsonOutputFactory();
    }

    @Bean
    //@Profile("english")
    @Primary
    public JsonOutputService jsonOutputServicePhoneAlert(JsonOutputFactory factory){
        return factory.createJsonOutputService(phoneAlert, firestation);
    }*/
    /*
    @Bean
    @Profile("spanish")
    public HelloWorldServiceInterface helloWorldServiceSpanish(HelloWorldFactory factory){
        return factory.createHelloWorldService("es");
    }

    @Bean
    public HelloWorldServiceInterface helloWorldServiceGerman(HelloWorldFactory factory){
        return factory.createHelloWorldService("de");
    }

    @Bean
    //@Profile("german")
    public HelloWorldServiceInterface helloWorldServiceJapan(HelloWorldFactory factory){
        return factory.createHelloWorldService("ja");
    }*/
}
