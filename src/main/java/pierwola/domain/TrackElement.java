package pierwola.domain;

import java.util.List;
import java.util.Set;

public interface TrackElement {

    Set<Talk> schedule(Set<Talk> talks);
    List<ScheduleEntry> getSchedule();

    TrackElement clone();
}
