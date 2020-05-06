package org.acme.rest.json;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class FruitResourceTest {

    @Test
    public void testFruitsEndpoint() {
	String payload = "[{\"description\":\"Winter fruit\",\"name\":\"Apple\"},{\"description\":\"Tropical fruit\",\"name\":\"Pineapple\"}]";
        given()
          .when().get("/fruits")
          .then()
             .statusCode(200)
             .body(is(payload));
    }

}
