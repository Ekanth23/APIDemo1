package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	public static JsonPath rawToJson(String response)
	{
		System.out.println("Response is");
		System.out.println(response);
		JsonPath js1= new JsonPath(response);
		return js1;
	}

}
