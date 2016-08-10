package voliy.samples.dog.controller;

import com.jayway.restassured.http.ContentType;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;
import voliy.samples.dog.service.DogService;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class DogControllerTest {
    @Test
    public void mustContainDogs() {
        DogService dogService = new DogService();
        dogService.init();
        DogController dogController = new DogController(dogService);

        given()
                .standaloneSetup(dogController).
        when().
                get("/dog").
        then().
                contentType(ContentType.JSON).
                statusCode(200).
                body("name", hasItems(Dog.names));
    }
}
