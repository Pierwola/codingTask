package pierwola.adapters;

import org.junit.jupiter.api.Test;
import pierwola.domain.Talk;

import java.net.URL;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class FileInputTest {

    @Test
    void givenCorrectInputFile_whenParsing_thenReturnTalks() {
        // given
        URL url = this.getClass().getClassLoader().getResource("correctInputFile.txt");
        String inputPath = url.getPath();

        // when
        FileInput input = new FileInput(inputPath);
        Set<Talk> talks = input.parseInput();

        // then
        assertThat(talks).isNotNull();
        assertThat(talks).hasSize(19);
    }

    @Test
    void givenMalformedInputFile_whenParsing_thenExpectException() {
        // given
        URL url = this.getClass().getClassLoader().getResource("inputWithoutFirstLine.txt");
        String inputPath = url.getPath();

        // when
        FileInput input = new FileInput(inputPath);
        assertThatCode(input::parseInput)
                // then
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Expected row count at the beginning of the file");
    }
}
