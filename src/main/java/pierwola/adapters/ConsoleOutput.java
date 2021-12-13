package pierwola.adapters;

import pierwola.domain.ScheduleEntry;
import pierwola.domain.Track;
import pierwola.domain.TrackElement;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsoleOutput implements Output {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mma");

    @Override
    public void output(List<Track> tracks) {
        if (tracks.isEmpty()) {
            return;
        }

        for (int i = 0; i < tracks.size(); i++) {
            System.out.println(String.format("Track %d:", i + 1));
            output(tracks.get(i));
        }
    }

    private void output(Track track) {
        List<TrackElement> trackElements = track.getTrackElements();
        for (TrackElement element : trackElements) {
            output(element);
        }
    }

    private void output(TrackElement element) {
        List<ScheduleEntry> schedule = element.getSchedule();
        for (ScheduleEntry entry : schedule) {
            output(entry);
        }
    }

    private void output(ScheduleEntry entry) {
        StringBuilder buffer = new StringBuilder();
        String startTime = entry.getStartTime().format(TIME_FORMATTER);
        buffer.append(startTime);
        buffer.append(" ");
        buffer.append(entry.getTitle());
        System.out.println(buffer.toString());
    }
}
