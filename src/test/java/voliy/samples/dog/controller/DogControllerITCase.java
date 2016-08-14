package voliy.samples.dog.controller;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class DogControllerITCase {
    private static final String BASE_URL = "/dog";

    @Test
    public void shouldContainDogs() {
        dogs().contentType(ContentType.JSON).statusCode(200).body("name", hasItems(Dog.names));
    }

    @Test
    public void shouldGetDogById() {
        dog(1).contentType(ContentType.JSON).statusCode(200).body("name", equalTo(Dog.names[0]));
    }

    private ValidatableResponse dogs() {
        return get(BASE_URL);
    }

    private ValidatableResponse dog(int id) {
        return get(BASE_URL + "/" + id);
    }

    private ValidatableResponse get(String url) {
        return when().get(url).then();
    }
}
