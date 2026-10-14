package model;

// Inheritance: reuses Item data; overriding displayItem() demonstrates polymorphism.
public class FoundItem extends Item {

    private String foundDate;

    public FoundItem(String itemId, String itemName, String description,
                     String location, String foundDate) {

        super(itemId, itemName, description, location);
        this.foundDate = foundDate;
    }

    public String getFoundDate() {
        return foundDate;
    }

    @Override
    public void displayItem() {
        // Polymorphism: FoundItem supplies its own version of Item's display contract.
        System.out.println("Found Item: " + getItemName());
        System.out.println("Location: " + getLocation());
        System.out.println("Found Date: " + foundDate);
    }
}
