package pierwola.domain;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BackpackProblemStrategyTest {

    private BackpackProblemStrategy strategy = new BackpackProblemStrategy();

    @Test
    void givenEmptyTalkSet_whenSelecting_returnsEmptySet() {
        // given
        Set<Talk> talks = Collections.emptySet();

        // when
        Set<Talk> result = strategy.selectTalks(talks, Integer.MAX_VALUE);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void givenSetWithShorterTalk_whenSelecting_returnsShorterTalk() {
        // given
        Set<Talk> talks = Sets.set(talk(30));

        // when
        Set<Talk> result = strategy.selectTalks(talks, 100);

        // then
        assertThat(result).hasSize(1);
        assertThat(result).hasSameElementsAs(talks);
    }

    @Test
    void givenSetWithSessionDurationTalk_whenSelecting_returnsTheTalk() {
        // given
        Set<Talk> talks = Sets.set(talk(100));

        // when
        Set<Talk> result = strategy.selectTalks(talks, 100);

        // then
        assertThat(result).hasSize(1);
        assertThat(result).hasSameElementsAs(talks);
    }

    @Test
    void givenShortTalks_whenSelecting_returnsManyTalks() {
        // given
        Set<Talk> talks = Sets.set(talk(31), talk(32), talk(30));

        // when
        Set<Talk> result = strategy.selectTalks(talks, 100);

        // then
        assertThat(result).hasSize(3);
        assertThat(result).hasSameElementsAs(talks);
    }

    @Test
    void givenTalksLongerThenSession_whenSelecting_returnsEmptySet() {
        // given
        Set<Talk> talks = Sets.set(talk(1001), talk(1002), talk(1003));

        // when
        Set<Talk> result = strategy.selectTalks(talks, 100);

        // then
        assertThat(result).isEmpty();
    }

    private Talk talk(int duration) {
        return new Talk("Talk", duration);
    }
}
