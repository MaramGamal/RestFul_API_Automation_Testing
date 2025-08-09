package tests;
import Base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Epic("Booking API Tests")
@Feature("Create Booking via URL")
public class CreateBooking_URL extends BaseTest {


    String bookingJSON = """
            {
                "firstname" : "Jim",
                "lastname" : "Brown",
                "totalprice" : 111,
                "depositpaid" : true,
                "bookingdates" : {
                    "checkin" : "2022-01-01",
                    "checkout" : "2022-01-10"
                },
                "additionalneeds" : "Breakfast"
            }
            
            """;

    @Story("Check status code 200 for POST /booking")
    @Test(description = "Validate that the API returns status code 200 when creating a booking")
    public void verifyStatusCode200_POST() {

        Allure.step("Send POST request to /booking and check status code", () -> {
            given()
                    .contentType("application/json")
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
    @Test(description = "Verify the response time of /booking endpoint for POST request")
    public void checkResponseTime_POST() {
        Allure.step("Send POST request and measure response time", () -> {
            given()
                    .contentType("application/json")
                    .body(bookingJSON)
                    .when()
                    .post("/booking")
                    .then()
                    .log().all()
                    .time(lessThan(4000L));
        });

        Allure.step("Validate response time is under 4 seconds");
    }
}