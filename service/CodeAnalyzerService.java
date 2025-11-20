package service;

import java.io.*;
import java.nio.file.*;

public class CodeAnalyzerService {
    private final File analysisDir = new File("data/analysis");
    
    public CodeAnalyzerService() {
        // Create analysis directory if it doesn't exist
        if (!analysisDir.exists()) {
            analysisDir.mkdirs();
        }
    }

    public void analyze(String path) {
        // Handle both relative and absolute paths
        File file = new File(path).getAbsoluteFile();
        
        // Check if file exists and is a file
        if (!file.exists()) {
            System.out.println("Error: File not found: " + file.getAbsolutePath());
            System.out.println("Current working directory: " + System.getProperty("user.dir"));
            return;
        }
        
        if (!file.isFile()) {
            System.out.println("Error: The path is not a file: " + file.getAbsolutePath());
            return;
        }

        try {
            // Detect file type based on extension
            String fileName = file.getName().toLowerCase();
            String language;
            if (fileName.endsWith(".py")) {
                language = "Python";
            } else if (fileName.endsWith(".cpp") || fileName.endsWith(".h") || fileName.endsWith(".hpp")) {
                language = "C++";
            } else if (fileName.endsWith(".java")) {
                language = "Java";
            } else {
                language = "Unknown";
            }

            int lines = 0, classes = 0, methods = 0, controlStructures = 0;
            boolean inMultiLineComment = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue; // Skip empty lines
                    
                    // Handle multi-line comments
                    if (line.startsWith("/*")) {
                        inMultiLineComment = true;
                    }
                    if (inMultiLineComment) {
                        if (line.endsWith("*/")) {
                            inMultiLineComment = false;
                        }
                        continue;
                    }
                    if (line.startsWith("//") || line.startsWith("#")) {
                        continue; // Skip single line comments
                    }

                    lines++;
                    
                    // Simple analysis (can be enhanced further)
                    if (language.equals("Java") && line.matches(".*\\bclass\\s+\\w+.*")) {
                        classes++;
                    }
                    if (line.matches(".*\\b(public|private|protected|static|void|int|String|double|float|boolean)\\s+\\w+\\(.*\\).*\\{\\s*$")) {
                        methods++;
                    }
                    if (line.matches(".*\\b(if|for|while|switch|case|default)\\s*\\(.*\\).*")) {
                        controlStructures++;
                    }
                }
            }

            // Generate report
            String report = String.format(
                "=== Code Analysis Report ===\n" +
                "File: %s\n" +
                "Language: %s\n" +
                "Lines of code: %d\n" +
                "Classes: %d\n" +
                "Methods/Functions: %d\n" +
                "Control structures: %d\n" +
                "===========================",
                file.getName(), language, lines, classes, methods, controlStructures
            );

            System.out.println(report);

            // Save report to file
            String reportFileName = "analysis_report_" + file.getName() + "_" + System.currentTimeMillis() + ".txt";
            File reportFile = new File(analysisDir, reportFileName);
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile))) {
                writer.write(report);
                System.out.println("\nAnalysis report saved to: " + reportFile.getAbsolutePath());
            }

        } catch (IOException e) {
            System.err.println("Error analyzing file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
