package pierwola.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BackpackProblemStrategy implements SolutionStrategy {

    public Set<Talk> selectTalks(Set<Talk> talks, int duration) {
        Set<Talk> talksCopy = new HashSet<>(talks);
        return selectTalks(talksCopy, duration, new HashSet<>());
    }

    private Set<Talk> selectTalks(Set<Talk> talks, int duration, Set<Talk> result) {
        Optional<Talk> foundTalk = selectTalk(talks, duration);
        foundTalk.ifPresent(talk -> {
            talks.remove(talk);
            selectTalks(talks, duration - talk.getDuration(), result);
            result.add(talk);
        });
        return result;
    }

    private Optional<Talk> selectTalk(Set<Talk> talks, int duration) {
        for (Talk talk : talks) {
            if (talk.getDuration() <= duration) {
                return Optional.of(talk);
            }
        }
        return Optional.empty();
    }
}
