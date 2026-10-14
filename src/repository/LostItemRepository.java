package repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.LostItem;

// Encapsulation: hides lost-item file operations behind save/load methods.
public class LostItemRepository {

    private String fileName = "lost_items.txt";

    // Save lost item into file
    public boolean saveLostItem(LostItem item) {

        try {

            try (FileWriter writer = new FileWriter(fileName, true)) {
                writer.write(
                item.getItemId() + "," +
                item.getItemName() + "," +
                item.getDescription() + "," +
                item.getLocation() + "," +
                item.getLostDate() + "\n"
                );
            }
            return true;

        } catch (IOException e) {

            System.out.println("Error while saving lost item.");
            return false;
        }
    }

    // Load lost items from file
    public ArrayList<LostItem> loadLostItems() {

        ArrayList<LostItem> items = new ArrayList<>();

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(fileName)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                LostItem item = new LostItem(
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

            System.out.println("No lost item file found.");
        }

        return items;
    }
}
