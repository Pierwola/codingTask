package pierwola.adapters;

import pierwola.domain.Talk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TalkParser {

    private static final String MINUTES_REGEX = "\\d+min";
    private static final String LIGHTNING = "lightning";
    private static final Pattern MINUTES_PATTERN = Pattern.compile(MINUTES_REGEX);
    private static final Pattern LIGHTNING_PATTERN = Pattern.compile("[\\D ]+ " + LIGHTNING);
    private static final Pattern EXPLICIT_TIME_PATTERN = Pattern.compile("[\\D ]+ " + MINUTES_REGEX);

    Talk parseTalk(String line) {
        if (isLightningTalk(line)) {
            return createLightningTalk(line);
        }

        if (isExplicitTimeTalk(line)) {
            return createExplicitTimeTalk(line);
        }

        throw new IllegalArgumentException(String.format("Could not parse line [%s]", line));
    }

    private boolean isLightningTalk(String line) {
        return LIGHTNING_PATTERN.matcher(line).matches();
    }

    private boolean isExplicitTimeTalk(String line) {
        return EXPLICIT_TIME_PATTERN.matcher(line).matches();
    }

    private Talk createLightningTalk(String line) {
        String title = line.substring(0, line.length() - LIGHTNING.length() - 1);
        return new Talk(title);
    }

    private Talk createExplicitTimeTalk(String line) {
        String title = line.substring(0, findMinutesStart(line) - 1);
        int minutes = retrieveMinutes(line);
        return new Talk(title, minutes);
    }

    private int retrieveMinutes(String line) {
        String minutesPart = line.substring(findMinutesStart(line), line.length() - 3);
        return Integer.parseInt(minutesPart);
    }

    private int findMinutesStart(String line) {
        Matcher matcher = MINUTES_PATTERN.matcher(line);
        matcher.find();
        return matcher.start();
    }
}
