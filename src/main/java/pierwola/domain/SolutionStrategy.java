package pierwola.domain;

import java.util.Set;

public interface SolutionStrategy {

    Set<Talk> selectTalks(Set<Talk> talks, int duration);
}