package uni.pu.fmi.repos;

import uni.pu.fmi.models.Admin;
import uni.pu.fmi.models.RepairRequest;

import java.util.ArrayList;
import java.util.List;

public class AdminRepairRequestRepo {

    private List<RepairRequest> repairRequests = new ArrayList<>();

    public List<RepairRequest> findAll() {
        return new ArrayList<>(repairRequests);
    }

    public void save(RepairRequest request) {
        repairRequests.add(request);
    }

    public void delete(RepairRequest request) {
        repairRequests.remove(request);
    }

    public boolean contains(RepairRequest request) {
        return repairRequests.contains(request);
    }

    public List<Admin> getAdmins(){
        List<Admin> admins = new ArrayList<>();
        admins.add(new Admin("ivan","Password123щ","test@test.com"));
        return admins;
    }

    public void add(Admin admin) {
        getAdmins().add(admin);
    }
}
