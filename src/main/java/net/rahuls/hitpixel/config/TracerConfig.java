package net.rahuls.hitpixel.config;

import brave.Tracer;
import brave.Tracing;
import lombok.extern.slf4j.Slf4j;
import net.rahuls.hitpixel.core.trace.DurationSpanHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TracerConfig {

    @Bean
    public Tracer tracer(@Value("${spring.application.name}") String applicationName) {
        try (Tracing tracing = Tracing.newBuilder()
                .localServiceName(applicationName)
                .addSpanHandler(new DurationSpanHandler())
                .build()) {
            return tracing.tracer();
        }
    }
}
