package service;

import java.util.ArrayList;

import model.Student;
import model.LostItem;
import model.FoundItem;
import model.Claim;

import exception.ItemNotFoundException;

import repository.LostItemRepository;
import repository.FoundItemRepository;
import repository.ClaimRepository;

// Encapsulation: callers use these service methods instead of coordinating persistence themselves.
// Association: this service works with repositories and domain objects through object references.
public class LostFoundService {

    private LostItemRepository lostItemRepository;
    private FoundItemRepository foundItemRepository;
    private ClaimRepository claimRepository;

    private ArrayList<Student> students;
    private ArrayList<LostItem> lostItems;
    private ArrayList<FoundItem> foundItems;
    private ArrayList<Claim> claims;

    public LostFoundService() {

        // Each service object owns its working collections and collaborates with repository objects.
        students = new ArrayList<>();
        lostItems = new ArrayList<>();
        foundItems = new ArrayList<>();
        claims = new ArrayList<>();

        lostItemRepository = new LostItemRepository();
        foundItemRepository = new FoundItemRepository();
        claimRepository = new ClaimRepository();
    }

    // Add Student
    public void addStudent(Student student) {

        students.add(student);
    }

    // Search Student by ID
    public Student searchStudent(String userId) {

        for (Student student : students) {

            if (student.getUserId().equals(userId)) {
                return student;
            }
        }

        return null;
    }

    // Add Lost Item
    public boolean addLostItem(LostItem item) {
        if (!lostItemRepository.saveLostItem(item)) {
            return false;
        }
        lostItems.add(item);
        return true;
    }

    // Add Found Item
    public boolean addFoundItem(FoundItem item) {
        if (!foundItemRepository.saveFoundItem(item)) {
            return false;
        }
        foundItems.add(item);
        return true;
    }

    // Add Claim
    public boolean addClaim(Claim claim) {
        if (!claimRepository.saveClaim(claim)) {
            return false;
        }
        claims.add(claim);
        return true;
    }

    // Get Lost Items
    public ArrayList<LostItem> getLostItems() {

        return lostItems;
    }

    // Get Found Items
    public ArrayList<FoundItem> getFoundItems() {

        return foundItems;
    }

    // Get Claims
    public ArrayList<Claim> getClaims() {

        return claims;
    }

    // Approve Claim
    public void approveClaim(String claimId) {

        for (Claim claim : claims) {

            if (claim.getClaimId().equals(claimId)) {

                claim.approveClaim();
                claimRepository.updateClaimStatus(claim);
                return;
            }
        }
    }

    // Reject Claim
    public void rejectClaim(String claimId) {

        for (Claim claim : claims) {

            if (claim.getClaimId().equals(claimId)) {

                claim.rejectClaim();
                claimRepository.updateClaimStatus(claim);
                return;
            }
        }
    }

    // Search Lost Item
    public LostItem searchLostItem(String itemName)
            throws ItemNotFoundException {

        for (LostItem item : lostItems) {

            if (item.getItemName().equalsIgnoreCase(itemName)) {

                return item;
            }
        }

        throw new ItemNotFoundException(
                "Lost item not found: " + itemName
        );
    }

    // Search Found Item
    public FoundItem searchFoundItem(String itemName)
            throws ItemNotFoundException {

        for (FoundItem item : foundItems) {

            if (item.getItemName().equalsIgnoreCase(itemName)) {

                return item;
            }
        }

        throw new ItemNotFoundException(
                "Found item not found: " + itemName
        );
    }
}
