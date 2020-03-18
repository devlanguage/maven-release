package org.basic.jdk.jdk8.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;

public class InstantWithJMockUnitTest {

    @Test
    public void givenInstantWithJMock_whenNow_thenGetFixedInstant() {
        String instantExpected = "2014-12-21T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        new MockUp<Instant>() {
            @Mock
            public Instant now() {
                return Instant.now(clock);
            }
        };

        Instant now = Instant.now();

        assertThat(now.toString()).isEqualTo(instantExpected);
    }

    @Test
    public void givenInstantWithExpectations_whenNow_thenGetFixedInstant() {
        Clock clock = Clock.fixed(Instant.parse("2014-12-23T10:15:30.00Z"), ZoneId.of("UTC"));
        Instant instantExpected = Instant.now(clock);
        new Expectations(Instant.class) {
            {
                Instant.now();
                result = instantExpected;
            }
        };

        Instant now = Instant.now();

        assertThat(now).isEqualTo(instantExpected);
    }
}
