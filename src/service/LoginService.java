package service;

import model.Student;
import model.Admin;
import model.User;



// Encapsulation: login rules stay inside this service. Returning the User superclass
// lets callers handle Student and Admin through one polymorphic type.
public class LoginService {

    private static final String STUDENT_ID = "S001";
    private static final String STUDENT_PASSWORD = "1234";
    private static final String ADMIN_ID = "A001";
    private static final String ADMIN_PASSWORD = "admin123";

    // Creates a Student subtype while exposing the shared User return type.
    public User studentLogin(String userId, String password) {

        if (STUDENT_ID.equalsIgnoreCase(userId) && STUDENT_PASSWORD.equals(password)) {

            return new Student(
                    STUDENT_ID,
                    "Himel",
                    "himel@gmail.com",
                    STUDENT_PASSWORD
            );
        }

        return null;
    }

    // Creates an Admin subtype while exposing the shared User return type.
    public User adminLogin(String userId, String password) {

        if (ADMIN_ID.equalsIgnoreCase(userId) && ADMIN_PASSWORD.equals(password)) {

            return new Admin(
                    ADMIN_ID,
                    "Admin",
                    "admin@gmail.com",
                    ADMIN_PASSWORD
            );
        }

        return null;
    }
}
