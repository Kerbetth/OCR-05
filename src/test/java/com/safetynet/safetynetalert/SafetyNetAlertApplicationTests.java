package com.safetynet.safetynetalert;

import com.safetynet.safetynetalert.SafetyNetAlertApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SafetyNetAlertApplication.class})
public class SafetyNetAlertApplicationTests {
	public static void main(final String[] args) {
		SpringApplication.run(SafetyNetAlertApplication.class, args);
	}
}
