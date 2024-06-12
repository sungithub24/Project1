package APITest1;

import java.io.File;

import org.apache.http.util.Asserts;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class CreateBooking1 {
				
	@Test
		public void createBook() {
		
		//Create JSON Body
			JSONObject body = new JSONObject();
			body.put("firstname", "Harry");
			body.put("lastname", "Chas");
			body.put("totalprice", 550);
			body.put("depositpaid", true);
			
			JSONObject bookingdates = new JSONObject();
			bookingdates.put("checkin", "2024-06-03");
			bookingdates.put("checkout", "2024-06-06");
			
			body.put("bookingdates", bookingdates);
			body.put("additionalneeds", "Breakfast");
						
			//Get Response
			Response response = RestAssured.given()
								.contentType(ContentType.JSON)
								.body(body.toString())
								.post("https://restful-booker.herokuapp.com/booking");
			//Print 
			response.print();
			
			//Verification
			Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it is not");
			
			//Schema Validation
			RestAssured.given()
						.header("Content-Type", "application/json")
						.body(body.toString())
						.when()
						.post("https://restful-booker.herokuapp.com/booking")
						.then()
						.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema.json"));
						
			//Verify all fields
			SoftAssert soft = new SoftAssert();
			
			String actualfirstname = response.jsonPath().getString("booking.firstname");
			soft.assertEquals(actualfirstname, "Harry", "FirstName in response is not expected.");
			
			String actuallastname = response.jsonPath().getString("booking.lastname");
			soft.assertEquals(actuallastname, "Chas", "LastName in response is not expected.");

			int totalprice = response.jsonPath().getInt("booking.totalprice");
			soft.assertEquals(totalprice, 550, "TotalPrice in response is not expected.");

			boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");
			soft.assertTrue(depositpaid, "Depositpaid in response is not expected.");

			String checkin = response.jsonPath().getString("booking.bookingdates.checkin");
			soft.assertEquals(checkin, "2024-06-03", "checkin in response is not expected.");

			String checkout = response.jsonPath().getString("booking.bookingdates.checkout");
			soft.assertEquals(checkout, "2024-06-06", "checkout in response is not expected.");

			String additionalneeds = response.jsonPath().getString("booking.additionalneeds");
			soft.assertEquals(additionalneeds, "Breakfast", "Additionalneeds in response is not expected.");

			soft.assertAll();
			
		}
}
