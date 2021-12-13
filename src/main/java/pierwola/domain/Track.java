package pierwola.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Track {

    private final List<TrackElement> trackElements;

    Track(List<TrackElement> elements) {
        this.trackElements = new ArrayList<>();
        for(TrackElement element : elements) {
            trackElements.add(element.clone());
        }
    }

    Set<Talk> scheduleTrack(Set<Talk> talks) {
        for(TrackElement element : trackElements) {
            talks = element.schedule(talks);
        }
        return talks;
    }

    public List<TrackElement> getTrackElements() {
        return new ArrayList<>(trackElements);
    }
}
