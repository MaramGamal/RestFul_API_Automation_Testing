package tests;
import Base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Epic("Booking Management")
@Feature("Partial Booking Update via Form URL Encoded")
public class PartialUpdateBooking_URL extends BaseTest {

    @Test(description = "Partial update booking using application/x-www-form-urlencoded with PUT method")
    @Description("This test performs a partial update on booking ID 1 using form-urlencoded data with fields firstname and lastname.")
    @Story("As a user, I want to partially update booking details using form-urlencoded data to modify only required fields.")
    public void PartialUpdateBooking_URL() {

        given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/x-www-form-urlencoded")
                .auth().preemptive().basic("admin", "password123")
                .formParam("firstname", "Jim")
                .formParam("lastname", "Brown")
                .when()
                .patch("https://restful-booker.herokuapp.com/booking/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo("Jim"))
                .body("lastname", equalTo("Brown"));
    }
}
