import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider="booksdata")
	public void addBook(String isbn, String aisle)
	{
		
		RestAssured.baseURI = "http://216.10.245.166";
		String response =given().log().all().header("Content-Type","application/json").
		body(Payload.addBook(isbn,aisle))
		.when()
		.post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		 
		 JsonPath js1 = ReusableMethods.rawToJson(response);
		 String id = js1.getString("ID");	
		 System.out.println(id); 	
	}
	@DataProvider(name="booksdata")
	public Object[][] getData(){
		{
			Object[][] data=new Object[][] { 
												{"sdfd", "34546"}, 
												{"sdfkgd", "5443958"}, 
												{"zcnved", "934357"},
											};
											
											return data; 
		}
	}
	
	//Add delete book method here 

}
