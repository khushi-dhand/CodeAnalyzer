package service;

import java.io.*;

public class CodeAnalyzerService {
    public void analyze(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File not found.");
            return;
        }

        int lines = 0, classes = 0, methods = 0, ifs = 0;
        String language = path.endsWith(".py") ? "Python" : path.endsWith(".cpp") ? "C++" : "Java";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines++;
                if (language.equals("Java") && line.contains("class")) classes++;
                if (line.contains("def") || line.contains("void") || line.contains("int ")) methods++;
                if (line.contains("if") || line.contains("for") || line.contains("while")) ifs++;
            }

            String report = String.format("Language: %s\nLines: %d\nClasses: %d\nMethods: %d\nControl Flow: %d",
                language, lines, classes, methods, ifs);

            System.out.println(report);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/analysis/report_" + file.getName() + ".txt"))) {
                writer.write(report);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
