package pierwola.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@RequiredArgsConstructor
@Getter
public class ScheduleEntry {

    private final LocalTime startTime;
    private final String title;
}
