package tests;

import Base.BaseTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("")
@Feature("")

public class GetBooking extends BaseTest {

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
                .get("/booking?firstname=Susan&lastname=Jones")
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
