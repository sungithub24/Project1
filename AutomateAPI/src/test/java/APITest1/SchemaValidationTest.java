package APITest1;

import java.io.File;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class SchemaValidationTest {
	
	@Test
	public void schemaValidate() {
				
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
		Response response = RestAssured.given().contentType(ContentType.JSON).
			body(body.toString()).post("https://restful-booker.herokuapp.com/booking");
		response.print();
		
		//JSON Schema Validation 
		
		//File InputJson = new File("C:\\Program Files\\eclipse-workspace\\AutomateAPI\\src\\test\\resources\\Input.json");
		RestAssured.given()
		//.baseUri("")
		.header("Content-Type", "application/json")
		.body(body.toString())
		.when()
		.post("https://restful-booker.herokuapp.com/booking")
		.then()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema.json"));
			
	}

}
