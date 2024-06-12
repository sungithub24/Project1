package APITest1;

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
import io.restassured.response.Response;

public class DeleteBooking1 {

	@Test
		public void deleteBooking() {
					
		//Create JSON Body
			JSONObject body = new JSONObject();
			body.put("firstname", "Harry");
			body.put("lastname", "Chas");
			body.put("totalprice", 550);
			body.put("depositpaid", true);
			
			JSONObject bookingdates1 = new JSONObject();
			bookingdates1.put("checkin", "2024-06-03");
			bookingdates1.put("checkout", "2024-06-06");
			
			body.put("bookingdates", bookingdates1);
			body.put("additionalneeds", "Breakfast");
						
			//Get Response
			Response responseCreate = RestAssured.given()
										.contentType(ContentType.JSON)
										.body(body.toString())
										.post("https://restful-booker.herokuapp.com/booking");
			responseCreate.print();

			//Verification
			Assert.assertEquals(responseCreate.getStatusCode(), 200, "Status code should be 200 but it is not");
			
			int bookingid1 = responseCreate.jsonPath().getInt("bookingid");
									
			//Delete Booking
			Response responseDelete = RestAssured.given().auth().preemptive().basic("admin", "password123").delete("https://restful-booker.herokuapp.com/booking/" + bookingid1);
			responseDelete.print();
			
			//verify Status code after update
			Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code should be 201 but it is not");
			
			//Check the same record whether it is available
			Response responseGet = RestAssured.given().contentType(ContentType.JSON).
			body(body.toString()).post("https://restful-booker.herokuapp.com/booking/" +bookingid1);
			responseGet.print();

			//soft.assertAll();
					
		}
}
