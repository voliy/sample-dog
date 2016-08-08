package voliy.samples.dog.controller;

import com.jayway.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;
import voliy.samples.dog.service.DogService;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class DogControllerTest extends Assert {
    @Test
    public void mustContainDogs() {
        DogController dogController = new DogController();
        DogService dogService = new DogService();
        dogService.init();
        dogController.setDogService(dogService);

        given()
                .standaloneSetup(dogController).
        when().
                get("/dogs").
        then().
                contentType(ContentType.JSON).
                statusCode(200).
                body("name", hasItems(Dog.names));
    }
}
