package com.safetynet.SafetyNetAlert;

import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.*;
import org.slf4j.*;

@SpringBootApplication
public class SafetyNetAlertApplication
{
    private static final Logger log;
    
    public static void main(final String[] args) {
        SpringApplication.run((Class)SafetyNetAlertApplication.class, args);
    }
    
    static {
        log = LoggerFactory.getLogger((Class)SafetyNetAlertApplication.class);
    }
}
