package com.example.demo.Interceptor;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.VerboseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;


@Configuration
public class RateLimiterInterceptor implements HandlerInterceptor {

    @Autowired
    private Bucket randomNumberBucket;

    @Autowired
    private Bucket randomStringBucket;

    private final AtomicLong randomNumberExhaustionTime = new AtomicLong(0);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().contains("/api1/random-number")) {
            return handleRateLimiterWithPenalty(randomNumberBucket, randomNumberExhaustionTime, response, Duration.ofMinutes(1));
        } else if (request.getRequestURI().contains("/api2/random-string")) {
            return handleRateLimiter(randomStringBucket, response);
        }
        return true;
    }

    private boolean handleRateLimiterWithPenalty(Bucket bucket, AtomicLong exhaustionTime, HttpServletResponse response, Duration penaltyDuration) {
        long now = Instant.now().toEpochMilli();
        long refillPeriod = Duration.ofMinutes(1).toMillis();
        long penaltyEnd = exhaustionTime.get() + penaltyDuration.toMillis() + refillPeriod;

        if (now < penaltyEnd && exhaustionTime.get() > 0) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", Long.toString((penaltyEnd - now) / 1000));
            return false;
        }


        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            exhaustionTime.set(0);
            response.addHeader("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()));
            return true;
        } else {
            exhaustionTime.set(now);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", Long.toString((penaltyEnd - now) / 1000));
            return false;
        }
    }

    private boolean handleRateLimiter(Bucket bucket, HttpServletResponse response) {
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()));
            return true;
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", Long.toString(probe.getNanosToWaitForRefill() / 1_000_000_000));
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) {
    }
}
