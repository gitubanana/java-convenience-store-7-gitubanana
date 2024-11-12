package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("== Spliter 테스트 ==")
public class SpliterTest {
    @Test
    @DisplayName("정규식을 토대로 토큰을 잘 분리할 수 있다.")
    void 토큰_분리() {
        final String TO_BE_SPLITED = "잘,분리,되나요,?,";
        final String REGEX = ",";
        final List<String> TOKENS = new ArrayList<>(List.of(
                "잘",
                "분리",
                "되나요",
                "?",
                ""
        ));
        Spliter spliter = new Spliter(TO_BE_SPLITED, REGEX);

        while (spliter.hasMoreTokens()) {
            assertThat(spliter.nextToken()).isEqualTo(TOKENS.getFirst());
            TOKENS.removeFirst();
        }

        assertThat(TOKENS.isEmpty()).isEqualTo(true);
    }
}
