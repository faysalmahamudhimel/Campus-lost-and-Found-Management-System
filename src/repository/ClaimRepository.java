package repository;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import model.Claim;

// Encapsulation: hides claim-file persistence details behind save/update operations.
public class ClaimRepository {

    private String fileName = "claims.txt";

    // Save claim into file
    public boolean saveClaim(Claim claim) {

        try {

            try (FileWriter writer = new FileWriter(fileName, true)) {
                writer.write(
                claim.getClaimId() + "," +
                claim.getStudent().getUserId() + "," +
                claim.getItem().getItemId() + "," +
                claim.getClaimDate() + "," +
                claim.getStatus() + "\n"
                );
            }
            return true;

        } catch (IOException e) {

            System.out.println("Error while saving claim.");
            return false;
        }
    }

    public void updateClaimStatus(Claim claim) {
        updateClaimStatus(claim.getClaimId(), claim.getStatus());
    }

    public boolean updateClaimStatus(String claimId, String status) {
        Path path = Path.of(fileName);
        try {
            List<String> lines = Files.exists(path)
                    ? Files.readAllLines(path)
                    : new ArrayList<>();
            boolean updated = false;
            for (int i = 0; i < lines.size(); i++) {
                String[] fields = lines.get(i).split(",", -1);
                if (fields.length >= 5 && fields[0].equals(claimId)) {
                    fields[4] = status;
                    lines.set(i, String.join(",", fields));
                    updated = true;
                }
            }
            if (updated) {
                Files.write(path, lines);
            }
            return updated;
        } catch (IOException e) {
            System.out.println("Error while updating claim status.");
            return false;
        }
    }
}
