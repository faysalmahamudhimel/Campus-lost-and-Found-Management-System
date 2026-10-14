package model;

// Encapsulation: location details are private and accessed through getters.
public class Location {

    // Encapsulation: private fields prevent direct access to location state.
    private String building;
    private String room;
    private String description;

    public Location(String building, String room, String description) {
        // Initialize this object's state through its constructor.
        this.building = building;
        this.room = room;
        this.description = description;
    }

    public String getBuilding() {
        return building;
    }

    public String getRoom() {
        return room;
    }

    public String getDescription() {
        return description;
    }

    public String getFullLocation() {
        // The object combines its private fields to provide a formatted value.
        return building + ", Room: " + room + ", " + description;
    }
}
