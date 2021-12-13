package pierwola.domain;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TalkScheduler {

    private final List<TrackElement> trackElements;

    public TalkScheduler(TrackElement... trackElements) {
        this.trackElements = List.of(trackElements);
    }

    public List<Track> scheduleTalks(Set<Talk> talks) {
        talks = new HashSet<>(talks);
        List<Track> tracks = new LinkedList<>();
        while(!talks.isEmpty()) {
            Track track = new Track(trackElements);
            talks = track.scheduleTrack(talks);
            tracks.add(track);
        }
        return tracks;
    }
}
