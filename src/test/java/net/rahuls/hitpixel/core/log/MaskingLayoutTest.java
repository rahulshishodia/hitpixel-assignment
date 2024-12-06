package net.rahuls.hitpixel.core.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.util.LogbackMDCAdapter;
import ch.qos.logback.core.pattern.FormattingConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class MaskingLayoutTest  {

    private final PatternLayout pl = new MaskingLayout();
    private final LoggerContext loggerContext = new LoggerContext();

    LogbackMDCAdapter logbackMDCAdapter = new LogbackMDCAdapter();
    Logger logger = loggerContext.getLogger(MaskingLayout.class);

    @BeforeEach
    public void setUp() {
        loggerContext.setMDCAdapter(logbackMDCAdapter);
        pl.setContext(loggerContext);
        pl.setPattern("%d %le [%t] %lo{30} - %m%n");
        pl.start();
    }

    LoggingEvent makeLoggingEvent(String msg, Exception ex) {
        return new LoggingEvent(FormattingConverter.class.getName(), logger, Level.INFO, msg, ex, null);
    }

    @Test
    void testAllPIIMasked() {
        String maskedMessage = pl.doLayout(
                makeLoggingEvent(
                        "username=johndoe, fullName=John Doe, email=johndoe@example.com, password=Password123, " +
                        "dateOfBirth=12/12/1985, creditCard=1234 5678 9101 1121",
                        null
        ));

        assertTrue(maskedMessage.contains("username=*"));
        assertTrue(maskedMessage.contains("fullName=*"));
        assertTrue(maskedMessage.contains("email=*"));
        assertTrue(maskedMessage.contains("password=*"));
        assertTrue(maskedMessage.contains("dateOfBirth=*"));
        assertTrue(maskedMessage.contains("creditCard=*"));
    }

}
