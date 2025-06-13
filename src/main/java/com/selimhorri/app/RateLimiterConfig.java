@Configuration
public class RateLimiterConfig {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterConfig.class);

    @Bean
    @Primary
    public KeyResolver ipKeyResolver() {
        logger.info(">>> Creating KeyResolver bean (ipKeyResolver)...");
        return exchange -> {
            String ip = getClientIP(exchange.getRequest());
            logger.info("Rate limiting key resolved to: {}", ip);
            return Mono.just(ip);
        };
    }

    private String getClientIP(ServerHttpRequest request) {
        String xForwardedFor = request.getHeaders().getFirst("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIP = request.getHeaders().getFirst("X-Real-IP");
        if (xRealIP != null && !xRealIP.isEmpty()) {
            return xRealIP;
        }
        
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        if (remoteAddress != null) {
            return remoteAddress.getAddress().getHostAddress();
        }
        
        return "unknown";
    }
}