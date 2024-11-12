package store.util;

import static store.constant.ErrorMessage.FILE_NOT_FOUND;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileLines {
    private final List<String> lines;

    public FileLines(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            lines = new ArrayList<>(reader.lines().toList());
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(FILE_NOT_FOUND.getMessage());
        }
    }

    public boolean hasMoreLines() {
        return !lines.isEmpty();
    }

    public String nextLine() {
        String line = lines.getFirst();

        lines.removeFirst();
        return line;
    }
}
