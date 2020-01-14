package com.safetynet.SafetyNetAlert;

import org.springframework.web.*;
import org.springframework.web.context.support.*;
import org.springframework.web.context.*;
import java.util.*;
import org.springframework.web.servlet.*;
import javax.servlet.*;

public class MyWebAppInitializer implements WebApplicationInitializer
{
    public void onStartup(final ServletContext container) {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.safetynet.SafetyNetAlert");
        container.addListener((EventListener)new ContextLoaderListener((WebApplicationContext)context));
        final ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", (Servlet)new DispatcherServlet((WebApplicationContext)context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(new String[] { "/SafetyNetAlert" });
    }
}
