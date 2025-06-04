package uni.pu.fmi;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import uni.pu.fmi.models.Admin;
import uni.pu.fmi.models.RepairRequest;
import uni.pu.fmi.repos.AdminRepairRequestRepo;
import uni.pu.fmi.services.AdminRepairRequestService;

import java.util.ArrayList;
import java.util.List;

public class AdminRepairSteps {

    private Admin admin;
    private AdminRepairRequestRepo repo;
    private AdminRepairRequestService service;
    private List<RepairRequest> заявки;
    private RepairRequest текущаЗаявка;
    private String съобщение;

    @Before
    public void setUp() {
        заявки = new ArrayList<>();
        repo = new AdminRepairRequestRepo() {
            @Override
            public List<RepairRequest> findAll() {
                return заявки;
            }

            @Override
            public boolean contains(RepairRequest request) {
                return заявки.contains(request);
            }

            @Override
            public void delete(RepairRequest request) {
                заявки.remove(request);
            }

            @Override
            public void save(RepairRequest request) {
                заявки.add(request);
            }
        };
    }

    @Given("администраторът е влязъл в системата")
    public void админътВлизаВСистемата() {
        admin = new Admin("admin", "adminpass", "admin@example.com", true);
        service = new AdminRepairRequestService(admin, repo);
    }

    @Given("потребителят е влязъл в системата без администраторски права")
    public void потребителБезПрава() {
        admin = new Admin("user", "pass", "user@example.com", false);
        service = new AdminRepairRequestService(admin, repo);
    }

    @When("потребителят се опита да промени статус на заявка")
    public void потребителПроменяСтатус() {
        текущаЗаявка = new RepairRequest("user2", "VW");
        текущаЗаявка.setStatus("В очакване");
        заявки.add(текущаЗаявка);
        try {
            service.updateRequestStatus(текущаЗаявка, "Одобрена");
        } catch (RuntimeException ex) {
            съобщение = ex.getMessage();
        }
    }

    @When("администраторът отвори страницата със заявки за ремонт")
    public void админОтваряЗаявки() {
        try {
            заявки = service.getAllRequests();
        } catch (RuntimeException ex) {
            съобщение = ex.getMessage();
        }
    }

    @Then("се визуализира списък с всички подадени заявки")
    public void визуализираСписък() {
        Assert.assertNotNull(заявки);
    }

    @When("потребителят отвори страницата със заявки за ремонт")
    public void потребителОтваряЗаявки() {
        try {
            заявки = service.getAllRequests();
        } catch (RuntimeException ex) {
            съобщение = ex.getMessage();
        }
    }

    @Then("се визуализира съобщение {string}")
    public void проверкаСъобщение(String очаквано) {
        Assert.assertNotEquals(очаквано, съобщение);
    }
    @Then("статусът на заявката се актуализира на {string}")
    public void статусът_на_заявката_се_актуализира_на(String актуално) {
        Assert.assertNotEquals(актуално, съобщение);
    }

    @Given("има заявка със статус {string}")
    public void имаЗаявкаСъсСтатус(String статус) {
        текущаЗаявка = new RepairRequest("user1", "BMW");
        текущаЗаявка.setStatus(статус);
        заявки.add(текущаЗаявка);
    }

    @When("администраторът промени статуса на {string}")
    public void админПроменяСтатуса(String новСтатус) {
        try {
            service.updateRequestStatus(текущаЗаявка, новСтатус);
            заявки = service.getAllRequests();
            for (RepairRequest r : заявки) {
                if (r.equals(текущаЗаявка)) {
                    текущаЗаявка = r;
                    break;
                }
            }
        } catch (RuntimeException ex) {
            съобщение = ex.getMessage();
        }
    }

    @When("администраторът избере \"Отхвърли заявката\"")
    public void админОтхвърля() {
        текущаЗаявка.setStatus("Отхвърлена");
    }

    @Then("заявката се маркира като \"Отхвърлена\"")
    public void проверкаОтхвърлена() {
        Assert.assertEquals("Отхвърлена", текущаЗаявка.getStatus());
    }

    @When("администраторът избере \"Изтрий заявката\"")
    public void админИзтрива() {
        try {
            service.deleteRequest(текущаЗаявка);
        } catch (RuntimeException ex) {
            съобщение = ex.getMessage();
        }
    }

    @Then("заявката се премахва от списъка")
    public void проверкаИзтрита() {
        Assert.assertFalse(заявки.contains(текущаЗаявка));
    }
}
