package se.codecadence.routes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;
import se.codecadence.routes.controller.RequestLoggerInterceptor;

/**
 * Web configuration class to register interceptors and other web-related configurations.
 */
@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private RequestLoggerInterceptor requestLoggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLoggerInterceptor);
    }

}
