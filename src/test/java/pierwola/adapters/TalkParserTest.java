package pierwola.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pierwola.domain.Talk;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class TalkParserTest {

    private TalkParser parser;

    @BeforeEach
    private void setup() {
        parser = new TalkParser();
    }

    @ParameterizedTest
    @MethodSource("oddTalks")
    void givenOddlyFormattedLine_whenParsing_thenThrowsException(String oddLine) {
        // given
        // when
        assertThatCode(
                () -> parser.parseTalk(oddLine))
                // then
                .hasMessage(String.format("Could not parse line [%s]", oddLine));
    }

    @ParameterizedTest
    @MethodSource("talksWithExplicitTime")
    void givenTalkWithTimeInMinutes_whenParsing_thenReturnTitleAndMinutes(String line, String title, int minutes) {
        // given
        // when
        Talk talk = parser.parseTalk(line);

        // then
        assertThat(talk.getTitle()).isEqualTo(title);
        assertThat(talk.printDuration()).isEqualTo(minutes + "min");
    }

    @ParameterizedTest
    @MethodSource("lightningTalks")
    void givenLightingTalk_whenParsing_thenReturnTitleAndLightningFlag(String line, String title) {
        // given
        // when
        Talk talk = parser.parseTalk(line);

        // then
        assertThat(talk.getTitle()).isEqualTo(title);
        assertThat(talk.printDuration()).isEqualTo("lightning");
    }

    static Stream<Arguments> talksWithExplicitTime() {
        return Stream.of(
                Arguments.of("Writing Fast Tests Using Selenium 60min", "Writing Fast Tests Using Selenium", 60),
                Arguments.of("Overdoing it in Java 45min", "Overdoing it in Java", 45),
                Arguments.of("AngularJS for the Masses 30min", "AngularJS for the Masses", 30),
                Arguments.of("Ruby Errors from Mismatched Gem Versions 45min", "Ruby Errors from Mismatched Gem Versions", 45),
                Arguments.of("Common Hibernate Errors 45min", "Common Hibernate Errors", 45),
                Arguments.of("Face-to-Face Communication 60min", "Face-to-Face Communication", 60),
                Arguments.of("Domain-Driven Development 45min", "Domain-Driven Development", 45),
                Arguments.of("A Perfect Sprint Planning 30min", "A Perfect Sprint Planning", 30),
                Arguments.of("Pair Programming vs Noise 45min", "Pair Programming vs Noise", 45),
                Arguments.of("Ruby on Rails: Why We Should Move On 60min", "Ruby on Rails: Why We Should Move On", 60),
                Arguments.of("Clojure Ate Scala (on my project) 45min", "Clojure Ate Scala (on my project)", 45),
                Arguments.of("Programming in the Boondocks of Seattle 30min", "Programming in the Boondocks of Seattle", 30),
                Arguments.of("Ant vs. Maven vs. Gradle Build Tool for Back-End Development 30min", "Ant vs. Maven vs. Gradle Build Tool for Back-End Development", 30),
                Arguments.of("Java Legacy App Maintenance 60min", "Java Legacy App Maintenance", 60),
                Arguments.of("A World Without Clinical Trials 30min", "A World Without Clinical Trials", 30),
                Arguments.of("User Interface CSS in AngularJS Apps 30min", "User Interface CSS in AngularJS Apps", 30)
        );
    }

    static Stream<Arguments> lightningTalks() {
        return Stream.of(
                Arguments.of("Rails for Java Developers lightning", "Rails for Java Developers"),
                Arguments.of("Pair Programming vs Noise lightning", "Pair Programming vs Noise"),
                Arguments.of("Ruby on Rails: Why We Should Move On lightning", "Ruby on Rails: Why We Should Move On"),
                Arguments.of("Clojure Ate Scala (on my project) lightning", "Clojure Ate Scala (on my project)"),
                Arguments.of("Programming in the Boondocks of Seattle lightning", "Programming in the Boondocks of Seattle"),
                Arguments.of("Ant vs. Maven vs. Gradle Build Tool for Back-End Development lightning", "Ant vs. Maven vs. Gradle Build Tool for Back-End Development"),
                Arguments.of("Java Legacy App Maintenance lightning", "Java Legacy App Maintenance"),
                Arguments.of("A World Without Clinical Trials lightning", "A World Without Clinical Trials"),
                Arguments.of("User Interface CSS in AngularJS Apps lightning", "User Interface CSS in AngularJS Apps")
        );
    }

    static Stream<Arguments> oddTalks() {
        return Stream.of(
                Arguments.of("What's New With Java 11 30min"),
                Arguments.of("Pair Programming vs Noise"),
                Arguments.of("60min"),
                Arguments.of("Very short talk min")
        );
    }
}
