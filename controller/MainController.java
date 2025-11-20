package controller;

import service.AuthService;
import service.CodeAnalyzerService;

import java.util.Scanner;

public class MainController {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthService authService = new AuthService();
    private final CodeAnalyzerService analyzer = new CodeAnalyzerService();

    public void start() {
        while (true) {
            System.out.println("\n===== CODE ANALYZER =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> authService.register();
                case 2 -> {
                    if (authService.login()) dashboard();
                }
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    public void dashboard() {
        while (true) {
            System.out.println("\n===== DASHBOARD =====");
            System.out.println("1. Analyze Code File");
            System.out.println("2. Logout");
            System.out.print("Enter choice: ");
            int ch = scanner.nextInt();
            scanner.nextLine();

            if (ch == 1) {
                System.out.print("Enter file path: ");
                String path = scanner.nextLine();
                analyzer.analyze(path);
            } else if (ch == 2) {
                System.out.println("Logged out.");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }
}
