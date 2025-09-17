package se.codecadence.routes.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class RequestLoggerInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = java.util.logging.Logger.getLogger(RequestLoggerInterceptor.class.getName());

    private final MeterRegistry meterRegistry;

    public RequestLoggerInterceptor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Store the timer object in the request attributes
        Timer.Sample timerSample = Timer.start(meterRegistry);
        request.setAttribute("timerSample", timerSample);

        // Log the request details if needed

        LOGGER.info("INC: Method=" + request.getMethod() + ", URI=" + request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // Not used for timing, timing is completed in afterCompletion
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // Retrieve the timer object from request attributes
        Timer.Sample timerSample = (Timer.Sample) request.getAttribute("timerSample");
        if (timerSample != null) {
            // Record the duration with Micrometer
            timerSample.stop(meterRegistry.timer("http.server.requests",
                    "uri", request.getRequestURI(),
                    "method", request.getMethod(),
                    "status", String.valueOf(response.getStatus())));
        }
    }
}
