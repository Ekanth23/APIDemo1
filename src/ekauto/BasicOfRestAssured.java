package ekauto;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;



public class BasicOfRestAssured {

	public static void main(String[] args){
		
		//Validate if add place API is working as expected
		
		//given - all input details 
		//When - submit the API (resource, http method 
		//Then - validate the response 
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-type", "application/json")
		.body(Payload.AddPlace())
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo ("APP"))
		.extract().response().asString();
		
		System.out.println(response);
		//Add place -->  -> Get place to validate if new Address is present in the response 
		
		JsonPath js = new JsonPath(response);
		String placeid = js.getString("place_id");
		System.out.println(placeid);
		
		String newAddress="Summer Walk, Africa";
		
		//Update place with new Address
		given().log().all().queryParam("key", "qaclick123").header("content-type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeid+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));	
		
		//GET API ==> Get place 
		//no header required 
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeid)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString(); 
		
		JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
		String actualAddress=js1.getString("address"); 
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
	
		
		
	}
}
