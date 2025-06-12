package com.selimhorri.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

@Configuration
public class RateLimiterConfig {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterConfig.class);

    @Bean
    public KeyResolver ipKeyResolver() {
        logger.info(">>> Creating KeyResolver bean (ipKeyResolver)...");
        return exchange -> {
            InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
            if (remoteAddress != null) {
                String ip = remoteAddress.getAddress().getHostAddress();
                logger.debug("Resolved IP address: {}", ip);
                return Mono.just(ip);
            }
            logger.warn("Remote address is null, defaulting to 'unknown'");
            return Mono.just("unknown");
        };
    }
}
