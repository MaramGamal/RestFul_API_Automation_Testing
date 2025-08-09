package tests;
import Base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("Booking API Tests")
@Feature("Create Booking via json")

public class CreateBooking_JSON extends BaseTest{


    private final String bookingJSON = """
            {
              "firstname" : "Susan",
              "lastname" : "Jones",
              "totalprice" : 111,
              "depositpaid" : true,
              "bookingdates" : {
                  "checkin" : "2025-01-01",
                  "checkout" : "2025-01-10"
              },
              "additionalneeds" : "Breakfast"
            }
            """;

    @Story("Check status code 200 for POST /booking")
    @Test(description = "Validate that the API returns status code 200")
    public void verifyStatusCode200() {

        Allure.step("Send POST request to /booking and check status code", () -> {
            given()
                    .header("Content-Type", "application/json")
                    .body(bookingJSON)
                    .when()
                    .post("/booking")
                    .then()
                    .log().all()
                    .statusCode(200);
        });

        Allure.step("Confirmed: Status code is 200");
    }

    @Story("Verify response time for POST /booking")
    @Test(description = "Verify the response time of /booking endpoint")
    public void checkResponseTime() {
        Allure.step("Send POST request and measure response time", () -> {
            given()
                    .header("Content-Type", "application/json")
                    .body(bookingJSON)
                    .when()
                    .post("/booking")
                    .then()
                    .log().all()
                    .time(lessThan(4000L));
        });

        Allure.step("Validate response time is under 4 seconds");
    }

    @Story("Create booking and verify bookingid in response")
    @Test(description = "Verify booking creation returns bookingid")
    public void createBookingAndVerifyId() {

        Response response = Allure.step(
                "Send POST request to /booking to create new booking",
                () -> given()
                        .header("Content-Type", "application/json")
                        .body(bookingJSON)
                        .post("/booking")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response()
        );

        Allure.step("Validate response contains bookingid", () -> {
            Map<String, Object> jsonData = response.jsonPath().getMap("");
            Assert.assertTrue(jsonData.containsKey("bookingid"), "Response body does not contain 'bookingid'");
        });
    }
}