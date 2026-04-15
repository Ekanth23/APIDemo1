package ekauto;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class SpecBuilderTest {
	

	
	
	public static void main (String[] args)
	{
		
		AddPlace p = new AddPlace(); 
		p.setAccuracy(50); 
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setName("Frontline house");
		p.setPhoneno("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		
		List<String> myList=new ArrayList<>(); 
		myList.add("shoe park"); 
		myList.add("shop"); 
		p.setTypes(myList); 
		
		Location loc = new Location();
		loc.setLat(-38.383494); 
		loc.setLang(33.427362); 
		p.setLocation(loc);
		
		RequestSpecification req = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON)
				.build();
		
		ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com"; 
		
		RequestSpecification res = given()
									.spec(req)
									.body(p);
		
		Response response = res.when().post("/maps/api/place/add/json")
		.then().spec(resspec).extract().response(); 
		
		String responseString = response.asString();
		System.out.println(responseString);
	}

}
