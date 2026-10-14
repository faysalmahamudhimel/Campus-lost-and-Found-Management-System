package model;

// Inheritance: Admin specializes the abstract User class; overriding provides polymorphism.
public class Admin extends User {

    public Admin(String userId, String name, String email, String password) {
        super(userId, name, email, password);
    }

    @Override
    public void showDashboard() {
        System.out.println("Admin Dashboard");
    }
}
