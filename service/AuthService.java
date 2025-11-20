package service;

import java.io.*;
import java.util.Scanner;

public class AuthService {
    private final File userFile = new File("data/users.txt");
    private final Scanner scanner = new Scanner(System.in);

    public void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (admin/user): ");
        String role = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.write(username + "," + password + "," + role);
            writer.newLine();
            System.out.println("Registered successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    System.out.println("Login successful! Welcome " + parts[0] + " (" + parts[2] + ")");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Invalid credentials.");
        return false;
    }
}
