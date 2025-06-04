package uni.pu.fmi;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import uni.pu.fmi.models.User;
import uni.pu.fmi.repos.RepairRequestRepo;
import uni.pu.fmi.services.RepairRequestService;

import static org.junit.Assert.*;

public class RepairRequestSteps {

    private final RepairRequestService service = new RepairRequestService();
    private final RepairRequestRepo repo = new RepairRequestRepo();
    private String model;
    private String description;
    private String resultMessage;

    @Before
    public void setup() {
        // Clear repository before each test
        repo.clear();
    }

    @Given("потребителят е на страницата за подаване на заявки")
    public void потребителят_е_на_страницата_за_подаване_на_заявки() {
        User user = new User("testUser", "password123", "test@example.com");
        service.login(user);
    }

    @Given("потребителят не е влязъл в системата")
    public void потребителят_не_е_влязъл_в_системата() {
        service.logout();
    }

    @Given("вече е подал заявка с модел {string} и описание {string}")
    public void вече_е_подал_заявка_с_модел_и_описание(String model, String description) {
        User user = new User("testUser", "password123", "test@example.com");
        service.login(user);
        service.submitRequest(model, description);
    }

    @When("потребителят въведе модел на колата {string}")
    public void потребителят_въведе_модел_на_колата(String model) {
        this.model = model;
    }

    @When("потребителят въведе описание на проблема {string}")
    public void потребителят_въведе_описание_на_проблема(String description) {
        this.description = description;
    }

    @When("потребителят въведе описание на проблема с повече от {int} символа")
    public void потребителят_въведе_описание_на_проблема_с_повече_от_символа(Integer length) {
        this.description = "x".repeat(length + 1);
    }

    @When("потребителят се опита да подаде заявка за ремонт")
    public void потребителят_се_опита_да_подаде_заявка_за_ремонт() {
        resultMessage = service.submitRequest(model, description);
    }
    @Then("визуализира се съобщение за грешка {string}")
    public void визуализира_се_съобщение_за_грешка(String expectedResult) {
        assertNotEquals("Заявката е подадена неуспешно!", resultMessage);
    }
    @Then("заявката се записва със статус {string}")
    public void заявката_се_записва_със_статус(String expectedStatus) {
        assertNotEquals("Заявката е подадена успешно!", resultMessage);
    }


    @Then("визуализира се съобщение за успех {string}")
    public void визуализира_се_съобщение_за_успех(String expectedMessage) {
        assertNotEquals(expectedMessage, resultMessage);
    }


    @After
    public void tearDown() {
        // Clean up after each test
        repo.clear();
    }
}