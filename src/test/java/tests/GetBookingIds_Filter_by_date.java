package tests;
import Base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("Booking API Tests")
@Feature("GetBookingIds Filter by Date")

public class GetBookingIds_Filter_by_date extends BaseTest {

    @Story("Check status code 200")
    @Test(description = "Validate that the API returns status code 200")
    public void verifyStatusCode200() {

        Allure.step("Send GET request to /booking and check status code", () -> {
            when()
                    .get("/booking")
                    .then()
                    .log().all()
                    .statusCode(200);
        });

        Allure.step("Confirmed: Status code is 200");
    }
    @Test(description = "Verify the response time of /booking endpoint")
    @Story(" check")
    public void checkResponseTime() {
        Allure.step("Send GET request and measure response time");

        when()
                .get("/booking")
                .then()
                .log()
                .all()
                .time(lessThan(4000L));

        Allure.step("Validate response time is under 4 seconds");
    }
    @Story("Response structure validation")
    @Description("Verify that the /booking endpoint returns an array in the response body")
    @Test(description = "Validate that booking list is returned as an array")
    public void validateResponseIsArray() {

        Response response = Allure.step("Send GET request to /booking endpoint and extract response", () ->
                when()
                        .get("/booking")
                        .then()
                        .log().all()
                        .extract().response()
        );

        Allure.step("Validate response body is an array", () -> {
            List<Map<String, Object>> jsonData = response.jsonPath().getList("");
            Assert.assertTrue(jsonData instanceof List, "Expected response to be an array");
        });
    }
}


