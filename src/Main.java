import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import model.Claim;
import model.FoundItem;
import model.LostItem;
import model.Student;
import model.User;
import repository.ClaimRepository;
import repository.FoundItemRepository;
import repository.LostItemRepository;
import service.LoginService;
import service.LostFoundService;


public class Main {
    
    private static final Scanner input = new Scanner(System.in);
    
    private static final LoginService loginService = new LoginService();

   
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("       CAMPUS LOST & FOUND");
        System.out.println("====================================");
        while (true) {
            System.out.println("\n1. Student Login\n2. Admin Login\n0. Exit");
           
            String choice = ask("Choose an option: ");
            if (choice.equals("0")) { System.out.println("Goodbye!"); return; }
            if (!choice.equals("1") && !choice.equals("2")) { error("Choose 1, 2, or 0."); continue; }
            String id = ask("User ID: ");
            String password = ask("Password: ");
            // Log in using the selected role and store the result as a User.
            User user = choice.equals("1")
                    ? loginService.studentLogin(id, password)
                    : loginService.adminLogin(id, password);
            if (user == null) { error("Invalid ID or password."); continue; }
            System.out.println("\nLogin successful. Welcome, " + user.getName() + "!");
            if (choice.equals("1")) studentMenu(user.getUserId()); else adminMenu();
        }
    }

    private static void studentMenu(String studentId) {
        // Keep the student menu open until the student logs out.
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- STUDENT MENU ---");
            System.out.println("1. Report lost item\n2. Report found item\n3. Search items or request claim\n4. My claims\n5. Logout");
            switch (ask("Choose an option: ")) {
                case "1": reportLost(); break;
                case "2": reportFound(); break;
                case "3": searchItems(studentId); break;
                case "4": showMyClaims(studentId); break;
                case "5": loggedIn = false; System.out.println("Logged out."); break;
                default: error("Choose a number from 1 to 5.");
            }
        }
    }

    private static void adminMenu() {
        // Keep the admin menu open until the admin logs out.
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View lost items\n2. View found items\n3. View claims\n4. Approve claim\n5. Reject claim\n6. Logout");
            switch (ask("Choose an option: ")) {
                case "1": showLostItems(); break;
                case "2": showFoundItems(); break;
                case "3": showClaims(); break;
                case "4": updateClaim("Approved"); break;
                case "5": updateClaim("Rejected"); break;
                case "6": loggedIn = false; System.out.println("Logged out."); break;
                default: error("Choose a number from 1 to 6.");
            }
        }
    }

    private static void reportLost() {
        // Create a model object with the lost item details and a generated ID.
        String name = required("Item name: ");
        String description = required("Description: ");
        String location = required("Lost location: ");
        String date = askDate("Lost date (YYYY-MM-DD, blank for today): ");
        String id = makeId("L");
        LostItem item = new LostItem(id, name, description, location, date);
        // Save the item through the service and display the result.
        if (new LostFoundService().addLostItem(item)) success("Lost item saved. Item ID: " + id);
        else error("Could not save the lost item.");
    }

    private static void reportFound() {
        // Create and save a FoundItem using the provided details.
        String name = required("Item name: ");
        String description = required("Description: ");
        String location = required("Found location: ");
        String date = askDate("Found date (YYYY-MM-DD, blank for today): ");
        String id = makeId("F");
        FoundItem item = new FoundItem(id, name, description, location, date);
        if (new LostFoundService().addFoundItem(item)) success("Found item saved. Item ID: " + id);
        else error("Could not save the found item.");
    }

    private static String askDate(String prompt) {
        // Use today's date if none is entered; ask again if the format is invalid.
        while (true) {
            String value = ask(prompt);
            if (value.isEmpty()) return LocalDate.now().toString();
            try { return LocalDate.parse(value).toString(); }
            catch (java.time.format.DateTimeParseException ex) { error("Use a valid date in YYYY-MM-DD format."); }
        }
    }

    private static void searchItems(String studentId) {
        // Search lost and found records by a partial item name.
        String query = required("Search item name: ").toLowerCase();
        List<FoundItem> matches = new ArrayList<>();
        boolean any = false;
        System.out.println("\n--- SEARCH RESULTS ---");
        // Display matching lost items; claims cannot be made directly for these items.
        for (LostItem item : new LostItemRepository().loadLostItems()) {
            if (item.getItemName().toLowerCase().contains(query)) {
                printItem("LOST", item.getItemId(), item.getItemName(), item.getDescription(), item.getLocation(), item.getLostDate());
                any = true;
            }
        }
        // Keep matching found items so the user can request a claim for one.
        for (FoundItem item : new FoundItemRepository().loadFoundItems()) {
            if (item.getItemName().toLowerCase().contains(query)) {
                printItem("FOUND", item.getItemId(), item.getItemName(), item.getDescription(), item.getLocation(), item.getFoundDate());
                matches.add(item);
                any = true;
            }
        }
        if (!any) { System.out.println("No matching items found."); return; }
        if (matches.isEmpty()) return;
        String selectedId = ask("Enter a FOUND item ID to request a claim, or press Enter to return: ");
        if (selectedId.isEmpty()) return;
        FoundItem selected = null;
        for (FoundItem item : matches) if (item.getItemId().equalsIgnoreCase(selectedId)) selected = item;
        if (selected == null) { error("That ID is not one of the matching found items."); return; }
        // Create and save a claim request for the selected found item
        String claimId = makeId("C");
        Claim claim = new Claim(claimId, new Student(studentId, studentId, "", ""), selected, LocalDate.now().toString());
        if (new LostFoundService().addClaim(claim)) success("Claim request submitted. Claim ID: " + claimId);
        else error("Could not save the claim request.");
    }

    private static void showMyClaims(String studentId) {
        // Display only the current student's claims from claims.txt.
        List<String> matches = new ArrayList<>();
        for (String line : readLines("claims.txt")) {
            String[] fields = line.split(",", -1);
            if (fields.length >= 5 && fields[1].equals(studentId)) matches.add(line);
        }
        printLines("YOUR CLAIMS", matches);
    }

    private static void showLostItems() {
        // Load all lost items from the repository and display them.
        System.out.println("\n--- LOST ITEMS ---");
        List<LostItem> items = new LostItemRepository().loadLostItems();
        if (items.isEmpty()) { System.out.println("No lost items found."); return; }
        for (LostItem i : items) printItem("LOST", i.getItemId(), i.getItemName(), i.getDescription(), i.getLocation(), i.getLostDate());
    }

    private static void showFoundItems() {
        // Load all found items from the repository and display them.
        System.out.println("\n--- FOUND ITEMS ---");
        List<FoundItem> items = new FoundItemRepository().loadFoundItems();
        if (items.isEmpty()) { System.out.println("No found items found."); return; }
        for (FoundItem i : items) printItem("FOUND", i.getItemId(), i.getItemName(), i.getDescription(), i.getLocation(), i.getFoundDate());
    }

    // Display all claims stored in the file for the admin.
    private static void showClaims() { printLines("ALL CLAIMS", readLines("claims.txt")); }

    private static void updateClaim(String status) {
        // Ask the repository to update the claim status by ID.
        String id = required("Claim ID: ");
        if (new ClaimRepository().updateClaimStatus(id, status)) success("Claim " + status.toLowerCase() + ".");
        else error("Claim ID not found or could not be updated.");
    }

    private static List<String> readLines(String file) {
        // Read all lines from a text file; return an empty list if it cannot be read.
        try { return java.nio.file.Files.readAllLines(java.nio.file.Path.of(file)); }
        catch (java.io.IOException ex) { return new ArrayList<>(); }
    }

    private static void printLines(String title, List<String> lines) {
        // Display records and replace delimiters with spaces for readability.
        System.out.println("\n--- " + title + " ---");
        if (lines.isEmpty()) System.out.println("No records found.");
        else for (String line : lines) System.out.println(line.replace(',', ' ').replace('|', ' '));
    }

    private static void printItem(String type, String id, String name, String description, String location, String date) {
        
        System.out.printf("%s | ID: %s | %s | %s | Location: %s | Date: %s%n", type, id, name, description, location, date);
    }

    private static String required(String prompt) {
        
        while (true) {
            String value = ask(prompt);
            if (!value.isEmpty()) return value;
            error("This field is required.");
        }
    }

    private static String ask(String prompt) {
    
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    private static String makeId(String prefix) {
        
        return prefix + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    
    private static void success(String message) { System.out.println("[OK] " + message); }
    private static void error(String message) { System.out.println("[!] " + message); }
}




















