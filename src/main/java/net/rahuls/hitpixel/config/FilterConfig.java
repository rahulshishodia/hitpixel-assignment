package net.rahuls.hitpixel.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<TraceResponseFilter> traceFilter(TraceResponseFilter traceResponseFilter) {
        FilterRegistrationBean<TraceResponseFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(traceResponseFilter);
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
