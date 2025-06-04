package uni.pu.fmi.models;

public class RepairRequest {

    private String username;
    private String model;
    private String issueDescription;
    private String status;

    public RepairRequest(String username, String model, String issueDescription) {
        this.username = username;
        this.model = model;
        this.issueDescription = issueDescription;
        this.status = "В очакване"; // Статус по подразбиране
    }

    public RepairRequest(String username, String model) {
    }

    public String getUsername() {
        return username;
    }

    public String getModel() {
        return model;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}