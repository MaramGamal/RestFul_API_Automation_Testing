package tests;

import Base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;

@Epic("Booking API Tests")
@Feature("GetBookingIds Endpoint Tests")
public class GetBookingIds_Query_all_IDs extends BaseTest {

    @Story("Query all booking IDs validations")
    @Test(description = "Validate booking IDs list and properties")
    public void checkthestatuscode() {
        Allure.step("Send GET request to /booking endpoint");
        when()
                .get("/booking")
                .then()
                .log().all()
                .statusCode(200);

        Allure.step("Validate status code is 200 ");
    }

    @Test(description = "Verify the response time of /booking endpoint")
    @Story(" check response time")

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

    @Test(description = "Booking list is not empty")
    @Story("Verify booking list is not empty")
    public void verifyBookingListIsNotEmpty() {

        Response response = Allure.step(
                "Send GET request to /booking endpoint and extract response",
                () -> when()
                        .get("/booking")
                        .then()
                        .log().all()
                        .extract().response()
        );

        Allure.step("Validate booking list is not empty", () -> {
            List<Map<String, Object>> jsonData = response.jsonPath().getList("");
            Assert.assertTrue(jsonData.size() > 0,
                    "Expected booking list to have at least one item, but it was empty");
        });
    }
}



