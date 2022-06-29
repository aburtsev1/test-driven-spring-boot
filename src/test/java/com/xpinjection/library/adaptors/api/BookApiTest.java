package com.xpinjection.library.adaptors.api;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookApiTest extends AbstractApiTest {
    @Test
    @DataSet(value = "default-books.xml", strategy = SeedStrategy.REFRESH)
    void ifBooksAreFoundByAuthorThenTheyAreAllReturned() {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("author", "Craig Walls")
        .when()
            .get("/books")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("size()", greaterThanOrEqualTo(1))
            .body("author", everyItem(equalTo("Craig Walls")))
            .body("name", contains("Spring in Action"));
    }

    @Test
    @DataSet(value = "default-books.xml", strategy = SeedStrategy.REFRESH)
    void ifBooksAreAddedTheyAreAllReturned() throws JSONException {
        JSONObject jsonObj = new JSONObject()
                .put("Spring in Action", "Craig Walls")
                .put("Hibernate in Action", "Who cares?");
        Response response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonObj.toString())
                .when()
                .post("/books");
        JsonPath jp = new JsonPath(response.asString());
        String nameValues = jp.get("name").toString();
        String authorValues = jp.get("author").toString();

        response
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("size()", equalTo(2));
        Assert.assertEquals("[Hibernate in Action, Spring in Action]", nameValues);
        Assert.assertEquals("[Who cares?, Craig Walls]", authorValues);
    }
}
