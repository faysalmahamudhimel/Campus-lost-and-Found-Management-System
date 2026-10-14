package model;

// Inheritance: Student specializes User; showDashboard() is overridden polymorphically.
public class Student extends User {

    public Student(String userId, String name, String email, String password) {
        super(userId, name, email, password);
    }

    @Override
    public void showDashboard() {
        System.out.println("Student Dashboard");
    }
}
