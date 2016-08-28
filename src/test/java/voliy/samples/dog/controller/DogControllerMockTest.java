package voliy.samples.dog.controller;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import com.jayway.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import voliy.samples.dog.dao.DogDao;
import voliy.samples.dog.model.Dog;
import voliy.samples.dog.service.DogService;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class DogControllerMockTest {
    private static final String BASE_URL = "/dog";

    private DogController dogController;

    @BeforeMethod
    private void init() {
        DogDao dogDao = new DogDao();
        dogDao.init();
        dogController = new DogController(new DogService(dogDao));
    }

    @Test
    public void shouldContainDogs() {
        dogs().contentType(ContentType.JSON).statusCode(200).body("name", hasItems(Dog.names));
    }

    @Test
    public void shouldGetDogById() {
        dog(1).contentType(ContentType.JSON).statusCode(200).body("name", equalTo(Dog.names[0]));
    }

    @Test
    public void shouldAddDogToStorage() {
        String expectedName = "Test Dog";
        addDog(new Dog(expectedName, null, 1.0, 1.0));
        dogs().contentType(ContentType.JSON).statusCode(200).body("name", hasItems(expectedName));
    }

    @Test
    public void shouldDeleteDogFromStorage() {
        deleteDog(1);
        ValidatableMockMvcResponse response = dog(1).statusCode(200);
        assertTrue(response.extract().asString().isEmpty());
    }

    private ValidatableMockMvcResponse dogs() {
        return get(BASE_URL);
    }

    private ValidatableMockMvcResponse dog(int id) {
        return get(BASE_URL + "/" + id);
    }

    private ValidatableMockMvcResponse get(String url) {
        return setup().when().get(url).then();
    }

    private void addDog(Dog dog) {
        setup().contentType(ContentType.JSON).body(dog).when().post(BASE_URL);
    }

    private void deleteDog(int id) {
        setup().when().delete(BASE_URL + "/" + id);
    }

    private MockMvcRequestSpecification setup() {
        return given().standaloneSetup(dogController);
    }
}
