package tests;
import Base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import config.ConfigReader;


public class CreateToken extends BaseTest {

    @Test(description = "Verify that token is created successfully with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Create authentication token")
    @Description("Send POST request with valid username and password and check if token is returned")
    public void createTokenWithValidCredentials() {

        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        String requestBody = String.format("""
            {
                "username": "%s",
                "password": "%s"
            }
            """, username, password);

        Allure.step("Send POST request to /auth/CreateToken with valid credentials");

        given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/auth")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", notNullValue());

        Allure.step("Validate token is present in the response");
    }
    @Test(description = "Verify that token creation fails with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Create authentication token with invalid data")
    @Description("Send POST request with invalid username or password and check for failure response")
    public void createTokenWithInvalidCredentials() {
        String invalidRequestBody = """
        {
            "username": "invalidUser",
            "password": "wrongPass"
        }
        """;

        Allure.step("Send POST request to /auth/CreateToken with invalid credentials");

        given()
                .header("Content-Type", "application/json")
                .body(invalidRequestBody)
                .when()
                .post("/auth/CreateToken")
                .then()
                .log().all()
                .statusCode(200) // بقت 200 بدل 401
                .body("reason", equalTo("Bad credentials"));

        Allure.step("Validate bad credentials error message is returned");
    }
}
