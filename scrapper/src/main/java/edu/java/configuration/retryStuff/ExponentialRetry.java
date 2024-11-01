package edu.java.configuration.retryStuff;

import edu.java.configuration.ApplicationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ResponseStatusException;
import reactor.util.retry.Retry;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "app", name = "retry-config.retry-type", havingValue = "exponential")
public class ExponentialRetry {
    @Bean
    public Retry backoffRetry(ApplicationConfig applicationConfig) {
        return Retry.backoff(applicationConfig.retryConfig().attempts(), applicationConfig.retryConfig().minDelay())
            .filter(throwable ->
                applicationConfig.retryConfig().statusCodes()
                    .contains(((ResponseStatusException) throwable).getStatusCode().value()))
            .doBeforeRetry(retrySignal -> log.info("retrying... -{}", retrySignal.totalRetries()));
    }
}
