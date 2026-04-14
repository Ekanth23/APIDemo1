package ekauto;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPath {
	
	public static void main(String[] args)
	{
		//mocked json
		JsonPath js = new JsonPath(Payload.CoursePrice());
		//print the no of courses
		//applied only on the array 
		int count = js.getInt("courses.size()");
		System.out.println(count);
		//print purchase amount 
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);
		String firstCourse = js.get("courses[0].title");
		System.out.println("firstCourse: "+firstCourse);
		
		//Print all course titles and their respective prices 
		for (int i=0; i<count; i++)
		{
			String courseTitle = js.get("courses["+i+"].title");
			
			String coursePrice=js.get("courses["+i+"].price").toString();
			
			System.out.println("Course title: "+courseTitle+"Course price: "+coursePrice);
			
			
		}
		
		System.out.println("Print no of copies sold by Appium");
		for (int i=0; i<count; i++)
		{
			String courseTitle = js.get("courses["+i+"].title");
			
			if(courseTitle.equalsIgnoreCase("Appium"))
			{
			
			
			System.out.println("No of copies price: "+js.get("courses["+i+"].copies").toString());
			
			break; 
			
		}
			
			System.out.println("Verify if sum of all course prices matches with Purchase Amount");
		
	}

}
}
