package boot;

import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.*;
import org.slf4j.*;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.safetynet.SafetyNetAlert")
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
