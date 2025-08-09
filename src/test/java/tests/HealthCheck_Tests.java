package tests;

import Base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("API Health Check")
@Feature("Ping/Health Endpoint Tests")

public class HealthCheck_Tests extends BaseTest {

    @Test(description = "Verify that the API server is up and responding with status code 201")
    @Story("Ping the server")
    @Description("Send GET request to /ping to verify server is reachable.")
    public void checkIfServerIsUp() {
        Allure.step("Send GET request to /ping endpoint");

        when()
                .get("/ping")
                .then()
                .log().all()
                .statusCode(201);

        Allure.step("Validate status code is 201 (Created)");
    }

    @Test(description = "Verify the response time of /ping endpoint")
    @Story("Performance check")
    @Description("Ensure /ping response time is less than 2000ms.")
    public void checkResponseTime() {
        Allure.step("Send GET request and measure response time");

        when()
                .get("/ping")
                .then()
                .log()
                .all()
                .time(lessThan(2000L));

        Allure.step("Validate response time is under 2 seconds");
    }

    @Test(description = "Validate the response body message from /ping endpoint")
    @Severity(SeverityLevel.NORMAL)
    @Story("Response content validation")
    @Description("Check if the response body has the correct success message.")
    public void checkResponseBodyMessage() {
        Allure.step("Send GET request and check body content");

        when()
                .get("/ping")
                .then()
                .log()
                .all()
                .body(equalTo("Created"));

        Allure.step("Validate response body is 'Created'");
    }
}
