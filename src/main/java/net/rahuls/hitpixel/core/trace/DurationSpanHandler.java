package net.rahuls.hitpixel.core.trace;

import brave.handler.MutableSpan;
import brave.handler.SpanHandler;
import brave.propagation.TraceContext;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
public class DurationSpanHandler extends SpanHandler {

    @Override
    public boolean begin(TraceContext context, MutableSpan span, TraceContext parent) {
        span.startTimestamp(Instant.now().getEpochSecond());
        return super.begin(context, span, parent);
    }

    @Override
    public boolean end(TraceContext context, MutableSpan span, SpanHandler.Cause cause) {
        if (span instanceof MutableSpan mutableSpan) {
            long startTimestamp = mutableSpan.startTimestamp();

            long epochTimeInNs = mutableSpan.finishTimestamp();
            long seconds = epochTimeInNs / 1_000_000_000;
            int nanoAdjustment = (int) (epochTimeInNs % 1_000_000_000);

            long finishTimestamp = Instant.ofEpochSecond(seconds, nanoAdjustment).toEpochMilli();

            log.info("Span {} took {} ms", mutableSpan.id(), finishTimestamp - startTimestamp);
        }
        return true;
    }
}
