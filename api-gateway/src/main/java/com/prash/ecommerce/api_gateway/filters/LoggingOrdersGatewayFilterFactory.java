        package com.prash.ecommerce.api_gateway.filters;

        import lombok.extern.slf4j.Slf4j;
        import org.springframework.cloud.gateway.filter.GatewayFilter;
        import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
        import org.springframework.stereotype.Component;
        import reactor.core.publisher.Mono;

        @Component
        @Slf4j
        public class LoggingOrdersGatewayFilterFactory extends AbstractGatewayFilterFactory<LoggingOrdersGatewayFilterFactory.Config> {

            public LoggingOrdersGatewayFilterFactory() {
                super(Config.class);
            }

            @Override
            public GatewayFilter apply(Config config) {
                return (exchange,chain)->{
                    log.info("Order filter Pre: {}", exchange.getRequest().getURI());
                    //return chain.filter(exchange);
                    return Mono.empty();

                };
            }

            public static class Config{

            }

        }
