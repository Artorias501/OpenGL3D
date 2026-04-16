package org.example.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
    static public String readFile(String fileName) {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            System.err.printf("File %s not found%n", fileName);
            return null;
        }

        String res;
        try {
            res = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return res;
    }
}
