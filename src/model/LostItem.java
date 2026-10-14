package model;

// Inheritance: reuses Item data; overriding displayItem() demonstrates polymorphism.
public class LostItem extends Item {

    private String lostDate;

    public LostItem(String itemId, String itemName, String description,
                    String location, String lostDate) {

        super(itemId, itemName, description, location);
        this.lostDate = lostDate;
    }

    public String getLostDate() {
        return lostDate;
    }

    @Override
    public void displayItem() {
        // Polymorphism: LostItem supplies its own version of Item's display contract.
        System.out.println("Lost Item: " + getItemName());
        System.out.println("Location: " + getLocation());
        System.out.println("Lost Date: " + lostDate);
    }
}
