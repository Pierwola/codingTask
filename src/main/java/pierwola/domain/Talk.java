package pierwola.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Talk {

    private static final int LIGHTNING_TALK_TIME = 5;

    private String title;
    private Integer minutes;
    private boolean lightning;

    public Talk(String title) {
        this.title = title;
        this.lightning = true;
    }

    public Talk(String title, int minutes) {
        this.title = title;
        this.minutes = minutes;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        if(minutes != null) {
            return minutes;
        }

        return LIGHTNING_TALK_TIME;
    }

    public String printDuration() {
        if(lightning) {
            return "lightning";
        }

        return String.format("%dmin", minutes);
    }

    public String toString() {
        return String.format("%s %s", getTitle(), printDuration());
    }
}
