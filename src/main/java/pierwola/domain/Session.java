package pierwola.domain;

import lombok.Getter;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
public class Session implements TrackElement {

    private final LocalTime start;
    private final LocalTime end;
    private final List<ScheduleEntry> schedule;

    public Session(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
        this.schedule = new LinkedList<>();
    }

    @Override
    public List<ScheduleEntry> getSchedule() {
        return new LinkedList<>(schedule);
    }

    public Set<Talk> schedule(Set<Talk> talks) {
        talks = new HashSet<>(talks);
        SolutionStrategy strategy = getStrategy();
        Set<Talk> sessionTalks = strategy.selectTalks(talks, getSessionDuration());
        LocalTime startTime = start;
        for (Talk talk : sessionTalks) {
            ScheduleEntry talkEntry = new ScheduleEntry(startTime, talk.toString());
            schedule.add(talkEntry);
            startTime = startTime.plusMinutes(talk.getDuration());
        }
        talks.removeAll(sessionTalks);
        return talks;
    }

    private int getSessionDuration() {
        int hours = end.getHour() - start.getHour();
        int minutes = end.getMinute() - start.getMinute();
        return hours * 60 + minutes;
    }

    protected SolutionStrategy getStrategy() {
        return new BackpackProblemStrategy();
    }

    public Session clone() {
        return new Session(start, end);
    }
}
