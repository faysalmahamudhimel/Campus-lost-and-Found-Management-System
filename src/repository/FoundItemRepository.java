


package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.FoundItem;

// Encapsulation: hides found-item file operations behind save/load methods.
public class FoundItemRepository {

    private String fileName = "found_items.txt";

    // Save found item into file
    public boolean saveFoundItem(FoundItem item) {

        try {

            try (FileWriter writer = new FileWriter(fileName, true)) {
                writer.write(
                item.getItemId() + "," +
                item.getItemName() + "," +
                item.getDescription() + "," +
                item.getLocation() + "," +
                item.getFoundDate() + "\n"
                );
            }
            return true;

        } catch (IOException e) {

            System.out.println("Error while saving found item.");
            return false;
        }
    }

    // Load found items from file
    public ArrayList<FoundItem> loadFoundItems() {

        ArrayList<FoundItem> items = new ArrayList<>();

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(fileName)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                FoundItem item = new FoundItem(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4]
                );

                items.add(item);
            }

            reader.close();

        } catch (IOException e) {

            System.out.println("No found item file found.");
        }

        return items;
    }
}
