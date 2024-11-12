package store.util;

import java.util.ArrayList;
import java.util.List;

public class Spliter {
    private static final int INCLUDE_TRAILING = -1;

    private final List<String> tokens;

    public Spliter(String toBeSplited, String regex) {
        tokens = new ArrayList<>(List.of(
                toBeSplited.split(regex, INCLUDE_TRAILING)
        ));
    }

    public boolean hasMoreTokens() {
        return !tokens.isEmpty();
    }

    public String nextToken() {
        String token = tokens.getFirst();

        tokens.removeFirst();
        return token;
    }
}
