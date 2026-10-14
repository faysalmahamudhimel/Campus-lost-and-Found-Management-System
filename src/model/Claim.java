package model;

// Encapsulation: claim state is private and controlled by getters/actions.
// Association: a Claim refers to the Student who filed it and the FoundItem being claimed.
public class Claim {

    private String claimId;
    private Student student;
    private FoundItem item;
    private String claimDate;
    private String status;

    public Claim(String claimId, Student student, FoundItem item,
                 String claimDate) {

        this.claimId = claimId;
        this.student = student;
        this.item = item;
        this.claimDate = claimDate;
        this.status = "Pending";
    }

    public String getClaimId() {
        return claimId;
    }

    public Student getStudent() {
        return student;
    }

    public FoundItem getItem() {
        return item;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public String getStatus() {
        return status;
    }

    public void approveClaim() {
        // Encapsulated state change: callers use this action instead of setting status directly.
        status = "Approved";
    }

    public void rejectClaim() {
        // Encapsulated state change: keep claim status updates inside the Claim object.
        status = "Rejected";
    }
}
