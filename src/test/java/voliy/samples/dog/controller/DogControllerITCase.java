package voliy.samples.dog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.springframework.util.StringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.testng.AssertJUnit.assertNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class DogControllerITCase {
    private static final String BASE_URL = "/dog";
    private static ObjectMapper objectMapper;

    @BeforeClass
    private static void init() {
        objectMapper = new ObjectMapper();
    }

    @Test public void savesAndLoadsDog() throws Exception {
        Dog expected = addDog(Dog.random());
        int dogId = expected.getId();
        Dog actual = getDog(dogId);

        assertReflectionEquals(expected, actual);
        deleteDog(dogId);
    }

    @Test public void addsDogAndUpdatesItsFields() throws Exception {
        Dog dog = addDog(Dog.random());
        int dogId = dog.getId();
        Dog expected = Dog.random();
        expected.setId(dogId);
        updateDog(expected);
        Dog actual = getDog(dogId);

        assertReflectionEquals(expected, actual);
        deleteDog(dogId);
    }

    @Test public void savesAndDeletesDog() throws Exception {
        Dog dog = addDog(Dog.random());
        int dogId = dog.getId();
        deleteDog(dogId);
        dog = getDog(dogId);
        assertNull(dog);
    }

    private Dog addDog(Dog dog) throws Exception {
        return given().contentType(ContentType.JSON).body(objectMapper.writeValueAsBytes(dog)).when().post(BASE_URL)
                .andReturn().as(Dog.class);
    }

    private Dog getDog(int id) {
        Response response = when().get(BASE_URL + "/" + id).andReturn();
        if (StringUtils.isEmpty(response.getBody().asString())) return null;
        return response.as(Dog.class);
    }

    private void updateDog(Dog dog) throws Exception {
        given().contentType(ContentType.JSON).body(objectMapper.writeValueAsBytes(dog)).when().put(BASE_URL);
    }

    private void deleteDog(int id) {
        when().delete(BASE_URL + "/" + id);
    }
}
