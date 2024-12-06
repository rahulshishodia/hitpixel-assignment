package net.rahuls.hitpixel.config;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rahuls.hitpixel.core.security.SecurityUtils;
import net.rahuls.hitpixel.data.entity.User;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class TraceResponseFilter extends OncePerRequestFilter {

    private static final String TRACE_ID_HEADER = "X-B3-TraceId";
    private static final String SPAN_ID_HEADER = "X-B3-SpanId";
    private static final String PARENT_SPAN_ID_HEADER = "X-B3-ParentSpanId";
    private static final String SAMPLED_HEADER = "X-B3-Sampled";

    private final Tracer tracer;
    private final SecurityUtils securityUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException {

        Span span = tracer.currentSpan();

        TraceContext context;
        String traceId;
        String spanId;
        String sampled;

        String parentSpanId = null;

        try {
            if (span != null) {
                context = span.context();
                traceId = context.traceIdString();
                spanId = context.spanIdString();
                parentSpanId = context.parentIdString();
                sampled = context.sampled() != null ? context.sampled().toString() : "0";
            } else {
                span = createNewSpan(request);
                context = span.context();
                traceId = context.traceIdString();
                spanId = context.spanIdString();
                sampled = "1";
            }

            MDC.put("traceId", traceId);
            MDC.put("spanId", spanId);

            setResponseHeaders(response, traceId, spanId, parentSpanId, sampled);

            filterChain.doFilter(request, response);

            if (!span.isNoop()) {
                span.finish();
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Error while setting trace response headers", e);
            throw new ServletException("Error while setting trace response headers", e);
        } finally {
            MDC.remove("traceId");
            MDC.remove("spanId");
        }
    }

    private Span createNewSpan(HttpServletRequest request) {
        String userId = Optional.ofNullable(securityUtils.getLoggedInUser())
                .map(User::getId)
                .map(String::valueOf)
                .orElse("anonymous");

        String requestId =
                Optional.ofNullable(request.getHeader("X-Request-ID")).orElse("");

        return tracer.newTrace()
                .tag("http.method", request.getMethod())
                .tag("http.url", request.getRequestURI())
                .tag("user.id", userId)
                .tag("client.ip", request.getRemoteAddr())
                .tag("request.id", requestId);
    }

    private void setResponseHeaders(
            HttpServletResponse response, String traceId, String spanId, String parentSpanId, String sampled) {
        if (traceId != null) {
            response.setHeader(TRACE_ID_HEADER, traceId);
            response.setHeader(SPAN_ID_HEADER, spanId);
            if (parentSpanId != null) {
                response.setHeader(PARENT_SPAN_ID_HEADER, parentSpanId);
            }
            response.setHeader(SAMPLED_HEADER, sampled);
        }
    }
}
