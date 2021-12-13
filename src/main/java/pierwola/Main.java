package pierwola;

import pierwola.adapters.ConsoleOutput;
import pierwola.adapters.FileInput;
import pierwola.adapters.Input;
import pierwola.adapters.Output;
import pierwola.domain.Break;
import pierwola.domain.Session;
import pierwola.domain.Talk;
import pierwola.domain.TalkScheduler;
import pierwola.domain.Track;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        validateArgument(args);
        Set<Talk> talks = readInput(args[0]);
        TalkScheduler scheduler = setupScheduler();
        List<Track> tracks = scheduler.scheduleTalks(talks);
        outputResult(tracks);
    }

    private static void validateArgument(String[] args) {
        if(args.length == 0) {
            throw new IllegalArgumentException("Expected one argument to the program");
        }
    }

    private static Set<Talk> readInput(String path) {
        Input input = new FileInput(path);
        return input.parseInput();
    }

    private static TalkScheduler setupScheduler() {
        Session morningSession = new Session(LocalTime.of(9, 0), LocalTime.of(12, 0));
        Break lunchBreak = new Break(LocalTime.of(12, 0), LocalTime.of(13, 0), "Lunch");
        Session afternoonSession = new Session(LocalTime.of(13, 0), LocalTime.of(17, 0));
        return new TalkScheduler(morningSession, lunchBreak, afternoonSession);
    }

    private static void outputResult(List<Track> tracks) {
        Output output = new ConsoleOutput();
        output.output(tracks);
    }
}
