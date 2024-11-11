package zad1;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

public class Futil {

    public static void processDir(String dirName, String resultFileName) {
        Path startDir = Paths.get(dirName);
        Path resultFile = Paths.get(resultFileName);

        try {
            Files.deleteIfExists(resultFile);
        } catch (IOException e) {
            System.err.println("Error deleting the result file: " + e.getMessage());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(resultFile, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            Files.walkFileTree(startDir, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,
                    new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                            try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("Cp1250"))) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    writer.write(line);
                                    writer.newLine();
                                }
                            } catch (IOException e) {
                                System.err.println("Error reading/writing file: " + e.getMessage());
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error processing directories: " + e.getMessage());
        }
    }
}