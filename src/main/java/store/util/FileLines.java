package store.util;

import static store.constant.ErrorMessage.FILE_NOT_FOUND;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileLines {
    private final BufferedReader reader;

    public FileLines(String file) {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(FILE_NOT_FOUND.getMessage());
        }
    }

    public String nextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }
}
