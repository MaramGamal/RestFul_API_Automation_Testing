package tests;
import Base.BaseTest;
import Utils.TokenGenerator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
@Epic("Booking Management")
@Feature("Delete Booking")

public class DeleteBooking extends BaseTest {
    @Test(description = "Delete booking with valid token and booking ID = 1")
    @Description("This test deletes an existing booking with ID 1 using a valid authentication token.")
    @Story("As a user, I want to delete a booking to remove unwanted or completed reservations.")
    public void DeleteBooking() {
        String token = TokenGenerator.generateToken();

        given()
                .header("Content-Type", "application/json")
                .cookie("token",token)
                .when()
                .delete("/booking/1")
                .then()
                .log().all()
                .statusCode(201);
    }
}


