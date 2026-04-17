package ekauto;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.Orders;

public class EcomAPITest {

	public static void main(String[] args) {
		
		//Login
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		LoginRequest loginreq=new LoginRequest();
		loginreq.setUserEmail("epostman@gmail.com"); 
		loginreq.setUserPassword("Password@123");
	
		//ssl certification : relaxedHTTPSValidation()
		RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginreq);
		
		LoginResponse loginres = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);

		System.out.println(loginres.getToken());
		String token = loginres.getToken();
		System.out.println(loginres.getUserId());
		String userId = loginres.getUserId();
		
		//Create Product : we will be using FORM parameter
		
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addHeader("authorization", token)
		.build();
		
		RequestSpecification addProductReq =given().log().all().spec(addProductBaseReq)
		.param("productName", "Laptop")
		.param("productAddedBy", userId)
		.param("productCategory", "fashion")
		.param("productSubCategory", "shirts")		
		.param("productPrice", "11500")	
		.param("productDescription", "Lenova")		
		.param("productFor", "men")	
		.multiPart("productImage", new File("C:\\Users\\acer\\Postman\\files\\tiger.jpg"));
		
		String addProductResponse = addProductReq.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		JsonPath jp = new JsonPath(addProductResponse); 
		
		String productId = jp.get("productId");
		
		System.out.println(productId);
		
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
				.setContentType(ContentType.JSON)
				.build();
		
		OrderDetails orderdetails = new OrderDetails(); 
		orderdetails.setCountry("India"); 
		orderdetails.setProductOrderedId(productId); 
		
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		
		orderDetailsList.add(orderdetails);
		
		Orders orders = new Orders(); 
		orders.setOrders(orderDetailsList);
		
		RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq)
		.body(orders);
		
		String responseAddorder=createOrderReq.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString(); 
		
		//Delete Product 
		RequestSpecification deleteProdBaseReq = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
		.setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteRrodReq = given().log().all().spec(deleteProdBaseReq).pathParam("productId", productId);
		
		String deleteRroductResponse = deleteRrodReq.when().delete("/api/ecom/product/delete-product/{productId}")
		.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(deleteRroductResponse); 
		
		String deletesuccessmsg = js1.get("message");
		
		Assert.assertEquals("Product Deleted Successfully", deletesuccessmsg);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
