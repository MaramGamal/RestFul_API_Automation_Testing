package tests;
import Base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Epic("Booking Management")
@Feature("Partial Booking Update")
public class PartialUpdateBooking_XML extends BaseTest {

    @Test(description = "Partial update booking using XML body, booking ID = 1")
    @Description("This test performs a partial update on booking ID 1 with XML payload containing only firstname and lastname fields.")
    @Story("As a user, I want to partially update booking information with minimal XML data to change only specific fields.")
    public void PartialUpdateBooking_XML() {

        String partialUpdateXML = """
            <booking>
                <firstname>James</firstname>
                <lastname>Brown</lastname>
            </booking>
        """;

        given()
                .header("Content-Type", "text/xml")
                .header("Accept", "application/xml")
                .auth().preemptive().basic("admin", "password123")
                .body(partialUpdateXML)
                .when()
                .patch("https://restful-booker.herokuapp.com/booking/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("booking.firstname", equalTo("James"))
                .body("booking.lastname", equalTo("Brown"));
    }
}
