package pierwola.adapters;

import lombok.SneakyThrows;
import pierwola.domain.Talk;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class FileInput implements Input {

    private final String path;

    public FileInput(String path) {
        this.path = path;
    }

    public Set<Talk> parseInput() {
        try (BufferedReader reader = getReader(path)) {
            int rowCount = getRowCount(reader);
            Set<Talk> talks = new HashSet<>();
            for (int i = 0; i < rowCount; i++) {
                Talk talk = readRow(reader);
                talks.add(talk);
            }
            return talks;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private BufferedReader getReader(String path) {
        try {
            return new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(path)));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Could not open given by path %s", path), e);
        }
    }

    @SneakyThrows
    private int getRowCount(BufferedReader reader) {
        String line = reader.readLine();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Expected row count at the beginning of the file");
        }
    }

    @SneakyThrows
    private Talk readRow(BufferedReader reader) {
        String line = reader.readLine();
        return getParser().parseTalk(line);
    }

    protected TalkParser getParser() {
        return new TalkParser();
    }
}
