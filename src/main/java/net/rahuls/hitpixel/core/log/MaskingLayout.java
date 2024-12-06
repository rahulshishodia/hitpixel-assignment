package net.rahuls.hitpixel.core.log;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class MaskingLayout extends PatternLayout {

    private static final List<String> PII_MASK_PATTERNS = List.of(
            "username=([^\\s]+)",
            "fullName=([^\\s]+)",
            "email=([\\w.-]+@[\\w.-]+\\.\\w+)",
            "password=([^\\s]+)",
            "dateOfBirth=(\\d{1,2}[-/]\\d{1,2}[-/]\\d{4})",
            "creditCard=(\\d{4}[-\\s]?\\d{4}[-\\s]?\\d{4}[-\\s]?\\d{4})"
    );

    @Override
    public String doLayout(ILoggingEvent event) {
        String message = super.doLayout(event);
        Pattern multilinePattern = Pattern.compile(String.join("|", PII_MASK_PATTERNS), Pattern.MULTILINE);
        StringBuilder sb = new StringBuilder(message);
        Matcher matcher = multilinePattern.matcher(sb);
        while (matcher.find()) {
            IntStream.rangeClosed(1, matcher.groupCount()).forEach(group -> {
                if (matcher.group(group) != null) {
                    IntStream.range(matcher.start(group), matcher.end(group)).forEach(i -> sb.setCharAt(i, '*'));
                }
            });
        }
        return sb.toString();
    }

}