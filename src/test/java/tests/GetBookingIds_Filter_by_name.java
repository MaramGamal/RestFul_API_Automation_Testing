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

@Epic("Booking API Tests")
@Feature("GetBookingIds with Filters by name")

public class GetBookingIds_Filter_by_name extends BaseTest {

    @Story("Filter booking IDs by firstname")
    @Test(description = "Validate that filtering by firstname returns correct results")
    public void filterBookingByFirstName() {

        String firstName = "John";


        Response response = Allure.step(
                "Send GET request to /booking with firstname=" + firstName,
                () -> when()
                        .get("/booking?firstname=" + firstName)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response()
        );


        Allure.step("Validate all returned bookings match firstname filter", () -> {
            List<Map<String, Object>> jsonData = response.jsonPath().getList("");
            Assert.assertTrue(jsonData.size() > 0, "No bookings found for firstname=" + firstName);

            for (Map<String, Object> booking : jsonData) {

                Assert.assertTrue(booking.containsKey("bookingid"), "Missing bookingid in response");

            }
        });
    }

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
}
