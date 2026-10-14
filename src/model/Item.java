package model;

// Abstraction: shared item data and an abstract display contract for item subtypes.
// Encapsulation: item state is private and exposed through getters.
public abstract class Item {

    private String itemId;
    private String itemName;
    private String description;
    private String location;

    public Item(String itemId, String itemName, String description, String location) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.location = location;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    // LostItem and FoundItem implement this differently (runtime polymorphism).
    public abstract void displayItem();
}
