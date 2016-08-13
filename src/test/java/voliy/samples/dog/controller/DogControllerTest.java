package voliy.samples.dog.controller;

import com.jayway.restassured.http.ContentType;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import voliy.samples.dog.dao.DogDao;
import voliy.samples.dog.model.Dog;
import voliy.samples.dog.service.DogService;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class DogControllerTest {
    private DogController dogController;

    @BeforeSuite
    private void init() {
        DogDao dogDao = new DogDao();
        dogDao.init();
        dogController = new DogController(new DogService(dogDao));
    }

    @Test
    public void mustContainDogs() {
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
