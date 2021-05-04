package org.example.RestAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;



public class RestAssuredTest {
    @Test
    public void call_Endpoint_Success(){
        when().get("https://jsonplaceholder.typicode.com/todos").then().assertThat().statusCode(200);
    }

    @Test
    public void todo4_Test(){
        when().get("https://jsonplaceholder.typicode.com/todos/4").then().assertThat().statusCode(200).and()
                .body("id",equalTo(4)).and()
                .body("title",equalTo("et porro tempora"));
    }
    @Test
    public void todo4_Test_ALT(){
        when().get("https://jsonplaceholder.typicode.com/todos").then().assertThat().statusCode(200).and()
                .body("[3].id",equalTo(4)).and()
                .body("[3].title",equalTo("et porro tempora"));
    }
    @Test
    public void contains198199Test(){
        when().get("https://jsonplaceholder.typicode.com/todos").then().assertThat().statusCode(200).and().
                body("id",hasItems(198,199));
    }
}
