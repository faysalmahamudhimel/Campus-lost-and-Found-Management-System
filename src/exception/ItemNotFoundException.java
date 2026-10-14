package exception;

// Inheritance: a domain-specific exception extends Java's built-in Exception class.
public class ItemNotFoundException extends Exception {

    // Constructor chaining: pass the message to the Exception superclass.
    public ItemNotFoundException(String message) {
        super(message);
    }
}
