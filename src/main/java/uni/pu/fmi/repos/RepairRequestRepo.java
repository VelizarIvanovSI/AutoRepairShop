package uni.pu.fmi.repos;

import uni.pu.fmi.models.RepairRequest;

import java.util.ArrayList;
import java.util.List;

public class RepairRequestRepo {
    private static final List<RepairRequest> requests = new ArrayList<>();

    public void add(RepairRequest request) {
        requests.add(request);
    }

    public List<RepairRequest> getAll() {
        return new ArrayList<>(requests);
    }

    public void clear() {
        requests.clear();
    }
}
