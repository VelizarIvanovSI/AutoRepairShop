package uni.pu.fmi.services;

import  uni.pu.fmi.models.Admin;
import uni.pu.fmi.models.RepairRequest;
import uni.pu.fmi.repos.AdminRepairRequestRepo;

import java.util.List;

public class AdminRepairRequestService {

    private Admin admin;
    private AdminRepairRequestRepo repo;

    public AdminRepairRequestService(Admin admin, AdminRepairRequestRepo repo) {
        this.admin = admin;
        this.repo = repo;
    }

    public List<RepairRequest> getAllRequests() {
        if (admin == null || !admin.canApproveRequests()) {
            throw new RuntimeException("Нямате права за достъп до тази секция");
        }
        return repo.findAll();
    }

    public void updateRequestStatus(RepairRequest request, String newStatus) {
        if (admin == null || !admin.canApproveRequests()) {
            throw new RuntimeException("Нямате права да редактирате заявки");
        }
        if (!repo.contains(request)) {
            throw new RuntimeException("Заявката не съществува");
        }

    }

    public void deleteRequest(RepairRequest request) {
        if (admin == null || !admin.canApproveRequests()) {
            throw new RuntimeException("Нямате права да изтривате заявки");
        }
        repo.delete(request);
    }

    public void addRequest(RepairRequest request) {
        if (admin == null || !admin.canApproveRequests()) {
            throw new RuntimeException("Нямате права да добавяте заявки");
        }
        repo.save(request);
    }
}