package com.cashback.Ratelimiter.Configuration;

import io.github.bucket4j.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.time.Duration;

@Component
public class ApiFilter extends GenericFilterBean {
    Bucket tokenBucket = Bucket4j.builder().addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)))).build();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.setHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
        } else {
            String path = request.getRequestURI();
            if (path.contains("/randomNumber")) {
                long waitForRefill = probe.getNanosToWaitForRefill();
                long penaltytime = Duration.ofMinutes(1).toNanos();
                long totaltime = waitForRefill + penaltytime;

                System.out.println(Duration.ofNanos(totaltime).toSeconds());
                response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(Duration.ofNanos(totaltime).toSeconds()));
                response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "you have exhausted you API Request Quota");
            } else {
                if(path.contains("/randomString")) {
                    long waitForRefill = probe.getNanosToWaitForRefill();
                    response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(Duration.ofNanos(waitForRefill).toSeconds()));
                    response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "you have exhausted you API Request Quota");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
