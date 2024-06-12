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

public class PartialUpdateBooking1 {
	
	@Test
		public void partailUpdate() {
				
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
			Response responseCreate = RestAssured.given().contentType(ContentType.JSON).
				body(body.toString()).post("https://restful-booker.herokuapp.com/booking");
			responseCreate.print();

			//Verification
			Assert.assertEquals(responseCreate.getStatusCode(), 200, "Status code should be 200 but it is not");
						
			//PartialUpdate Booking
			JSONObject body1 = new JSONObject();
			body1.put("firstname", "John");
			body1.put("totalprice", 900);
			
			int bookingid1 = responseCreate.jsonPath().getInt("bookingid");
			
			//Get Response
			Response responseupdate = RestAssured.given().auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON).
				body(body1.toString()).patch("https://restful-booker.herokuapp.com/booking/" + bookingid1);
			responseupdate.print();
			
			//verify Status code after update
			Assert.assertEquals(responseupdate.getStatusCode(), 200, "Status code should be 200 but it is not");
			
			//Verify all fields
			SoftAssert soft = new SoftAssert();
			String actualfirstname = responseupdate.jsonPath().getString("firstname");
			soft.assertEquals(actualfirstname, "John", "FirstName in response is not expected.");
			
			int totalprice = responseupdate.jsonPath().getInt("totalprice");
			soft.assertEquals(totalprice, 900, "TotalPrice in response is not expected.");
							
			soft.assertAll();
					
		}
}
