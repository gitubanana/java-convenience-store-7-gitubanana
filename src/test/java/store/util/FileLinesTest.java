package store.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.constant.ErrorMessage.FILE_NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("== FileLines 테스트 ==")
public class FileLinesTest {
    @Test
    @DisplayName("파일을 잘 읽을 수 있다.")
    void 파일_읽기() {
        final String TEST_FILE = "src/test/java/store/util/fileLinesTestFile.txt";
        final List<String> CORRECT_LINES = new ArrayList<>(List.of(
                "안녕하세요.",
                "편의점 너무 어려워요.",
                "다들 힘내세요!"
        ));
        final FileLines fileLines = new FileLines(TEST_FILE);

        while (fileLines.hasMoreLines()) {
            assertThat(fileLines.nextLine()).isEqualTo(CORRECT_LINES.getFirst());
            CORRECT_LINES.removeFirst();
        }

        assertThat(CORRECT_LINES.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("존재하지 않는 파일일 경우 예외를 발생시킬 수 있다.")
    void 존재하지_않는_파일() {
        final String NOT_FOUND_FILE = "존재하지 않는 파일";

        assertThatThrownBy(() -> new FileLines(NOT_FOUND_FILE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(FILE_NOT_FOUND.getMessage());
    }
}
