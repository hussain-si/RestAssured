package Project1.Livpro2;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;

import static io.restassured.RestAssured.*;

public class Basics {

	public static void main(String[] args) {
		//Add map API
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response_add = given().log().all().queryParam("key ", "qaclick123").header("Content-Type", "application/json").
		body(payload.AddPlace()).
		when().post("/maps/api/place/add/json").
		then().assertThat().log().all().statusCode(200).body("scope", equalTo("APP")).
		header("server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		System.out.println(response_add);
		JsonPath js = new JsonPath(response_add);
		String place_id = js.getString("place_id");
		System.out.println(place_id);
		
		//update place
		String new_place_id ="Bangalore karnataka";
		given().log().all().queryParam("key ", "qaclick123").
		header("Content-Type", "application/json").body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\""+new_place_id+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get place
		String getPlaceResponse=	given().log().all().queryParam("key", "qaclick123")
				.queryParam("place_id",place_id)
				.when().get("maps/api/place/get/json")
				.then().assertThat().log().all().statusCode(200).extract().response().asString();
			JsonPath js1=ReUsableMethods.rawToJson(getPlaceResponse);
			String actualAddress =js1.getString("address");
			System.out.println(actualAddress);
			Assert.assertEquals(actualAddress, new_place_id);
			//Cucumber Junit, Testng
			
			}
}
