package pierwola.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class Break implements TrackElement {

    private final LocalTime start;
    private final LocalTime end;
    private final String title;

    @Override
    public Set<Talk> schedule(Set<Talk> talks) {
        // DO NOTHING
        return talks;
    }

    @Override
    public List<ScheduleEntry> getSchedule() {
        return List.of(new ScheduleEntry(start, title));
    }

    public Break clone() {
        return new Break(start, end, title);
    }
}
