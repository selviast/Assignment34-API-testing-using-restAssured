package tests.categorySport;

import body.categorySport.CreateCategorySportBody;
import body.categorySport.UpdateCategorySportBody;
import body.sportActivity.CreateSportActivityBody;
import body.sportActivity.UpdateSportActivityBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class UpdateCategory {
    @Test
    public void updateCategoryFromJson() throws Exception {
        // Set Base URI dari ConfigReader
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // Ambil token dari file token.json
        String tokenFile = "src/resources/json/token.json";
        String tokenContent = new String(Files.readAllBytes(Paths.get(tokenFile)));
        JSONObject tokenJson = new JSONObject(tokenContent);
        String token = tokenJson.getString("token");

        // Ambil activity_id dari file activity_id.json
        String categoryFile = "src/resources/json/category_id.json";
        String categoryContent = new String(Files.readAllBytes(Paths.get(categoryFile)));
        JSONObject categoryJson = new JSONObject(categoryContent);
        int categoryId = categoryJson.getInt("category_id");

        // Baca body dari file JSON
        UpdateCategorySportBody bodyHelper = new UpdateCategorySportBody();
        JSONObject requestBody = bodyHelper.getBodyFromFile("src/resources/json/update_category.json");

        // Kirim request PUT
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/sport-categories/update/" + categoryId)
                .then()
                .extract().response();

        // Print response
        System.out.println("Response: " + response.asString());
        System.out.println("Category ID: " + categoryId);

        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Headers: " + response.getHeaders());


        // Validasi
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("message"), "data saved");
        Assert.assertFalse(response.jsonPath().getBoolean("error"));
    }
}
