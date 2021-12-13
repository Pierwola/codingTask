package pierwola.domain;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class TalkSchedulerTest {

    @Test
    void givenSetupTalkScheduler_whenSchedulingEvent_thenNoneOfTheTalksIsOmittedOrDuplicated() {
        // given
        TalkScheduler scheduler = new TalkScheduler(givenSessions());
        Set<Talk> talks = givenTalks();

        // when
        List<Track> tracks = scheduler.scheduleTalks(talks);

        // then
        Set<String> scheduledTalks = tracks.stream()
                .map(Track::getTrackElements)
                .reduce(new ArrayList<>(), (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                })
                .stream().map(TrackElement::getSchedule)
                .reduce(new ArrayList<>(), (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                }).stream().map(ScheduleEntry::getTitle)
                .collect(Collectors.toSet());

        assertThat(scheduledTalks).hasSameSizeAs(talks);
    }

    @Test
    void givenSetupTalkScheduler_whenSchedulingEvent_thenTalksInSessionStartAtSessionStart() {
        // given
        TalkScheduler scheduler = new TalkScheduler(givenSessions());
        Set<Talk> talks = givenTalks();

        // when
        List<Track> tracks = scheduler.scheduleTalks(talks);

        // then
        tracks.forEach(track -> track.getTrackElements()
                .forEach(session -> {
                    List<ScheduleEntry> scheduleEntries = session.getSchedule();
                    LocalTime firstTalkStart = scheduleEntries.get(0).getStartTime();
                    LocalTime sessionStart = ((Session) session).getStart();
                    assertThat(firstTalkStart).isEqualTo(sessionStart);
                })
        );
    }

    @Test
    void givenSetupTalkScheduler_whenSchedulingEvent_thenTalksInSessionEndAtOrBeforeSessionEnd() {
        // given
        TalkScheduler scheduler = new TalkScheduler(givenSessions());
        Set<Talk> talks = givenTalks();

        // when
        List<Track> tracks = scheduler.scheduleTalks(talks);

        // then
        tracks.forEach(track -> track.getTrackElements()
                .forEach(session -> {
                    List<ScheduleEntry> scheduleEntries = session.getSchedule();
                    ScheduleEntry lastScheduleEntry = scheduleEntries.get(scheduleEntries.size() - 1);
                    Talk lastTalk = findByScheduleEntry(talks, lastScheduleEntry.getTitle());
                    LocalTime lastTalkEnd = lastScheduleEntry.getStartTime().plusMinutes(lastTalk.getDuration());
                    LocalTime sessionEnd = ((Session) session).getEnd();
                    assertThat(lastTalkEnd).isBeforeOrEqualTo(sessionEnd);
                })
        );
    }

    @Test
    void givenSetupTalkScheduler_whenSchedulingEvent_thenTalksInSessionDoNotOverlap() {
        // given
        TalkScheduler scheduler = new TalkScheduler(givenSessions());
        Set<Talk> talks = givenTalks();

        // when
        List<Track> tracks = scheduler.scheduleTalks(talks);

        // then
        tracks.forEach(track -> track.getTrackElements()
                .forEach(session -> {
                    List<ScheduleEntry> scheduleEntries = session.getSchedule();
                    for (int i = 0; i < scheduleEntries.size() - 1; i++) {
                        LocalTime earlierTalkStart = scheduleEntries.get(i).getStartTime();
                        Talk earlierTalk = findByScheduleEntry(talks, scheduleEntries.get((i)).getTitle());
                        LocalTime earlierTalkEnd = earlierTalkStart.plusMinutes(earlierTalk.getDuration());
                        LocalTime laterTalkStart = scheduleEntries.get(i + 1).getStartTime();
                        assertThat(laterTalkStart).isEqualTo(earlierTalkEnd);
                    }
                })
        );
    }

    private TrackElement[] givenSessions() {
        return new TrackElement[]{new Session(LocalTime.of(9, 0), LocalTime.of(11, 0)),
                new Session(LocalTime.of(12, 0), LocalTime.of(14, 0))};
    }

    private Set<Talk> givenTalks() {
        return Sets.set(
                talk(30),
                talk(32),
                talk(28),
                talk(40),
                talk(38),
                talk(42),
                talk(58),
                talk(60),
                talk(62));
    }

    private Talk talk(int duration) {
        return new Talk("Talk" + duration, duration);
    }

    private Talk findByScheduleEntry(Set<Talk> talks, String scheduleEntryTitle) {
        String talkTitle = scheduleEntryTitle.substring(0, scheduleEntryTitle.length() - 6);
        return talks.stream()
                .filter(talk -> talk.getTitle().equals(talkTitle))
                .findAny()
                .get();
    }
}
