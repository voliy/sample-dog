package voliy.samples.dog.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;

import java.util.Arrays;
import java.util.List;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_ORDER;

@ContextConfiguration(locations = {"classpath:context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DogControllerMockTest extends AbstractTestNGSpringContextTests {
    private static final String BASE_URL = "/dog";
    private static ObjectMapper objectMapper;
    @Autowired private DogController dogController;

    @BeforeClass private static void init() {
        objectMapper = new ObjectMapper();
    }

    @Test public void savesAndLoadsDog() throws Exception {
        Dog expected = addDog(Dog.random());
        Dog actual = getDog(expected.getId());
        assertReflectionEquals(expected, actual);
    }

    @Test public void addsDogAndUpdatesItsFields() throws Exception {
        Dog dog = addDog(Dog.random());
        int dogId = dog.getId();
        Dog expected = Dog.random();
        expected.setId(dogId);
        updateDog(expected);
        Dog actual = getDog(dogId);
        assertReflectionEquals(expected, actual);
    }

    @Test public void savesAndDeletesDog() throws Exception {
        Dog dog = addDog(Dog.random());
        int dogId = dog.getId();
        deleteDog(dogId);
        dog = getDog(dogId);
        assertNull(dog);
    }

    @Test public void addsDogsAndLoadsAllOfThem() throws Exception {
        Dog firstDog = addDog(Dog.random());
        Dog secondDog = addDog(Dog.random());
        List<Dog> allDogs = loadAllDogs();
        assertNotNull(allDogs);
        assertEquals(2, allDogs.size());
        assertReflectionEquals(Arrays.asList(secondDog, firstDog), allDogs, LENIENT_ORDER);
    }

    private Dog addDog(Dog dog) throws Exception {
        String response = setup().contentType(ContentType.JSON).body(dog).when().post(BASE_URL).andReturn()
                .getMvcResult().getResponse().getContentAsString();
        return objectMapper.readValue(response, Dog.class);
    }

    private Dog getDog(int id) throws Exception {
        String response = setup().when().get(BASE_URL + "/" + id).andReturn().getMvcResult().getResponse()
                .getContentAsString();
        if (StringUtils.isEmpty(response)) return null;
        return objectMapper.readValue(response, Dog.class);
    }

    private List<Dog> loadAllDogs() throws Exception {
        String response = setup().when().get(BASE_URL).andReturn().getMvcResult().getResponse()
                .getContentAsString();
        if (StringUtils.isEmpty(response)) return null;
        return objectMapper.readValue(response, new TypeReference<List<Dog>>() {});
    }

    private void updateDog(Dog dog) throws Exception {
        setup().contentType(ContentType.JSON).body(dog).when().put(BASE_URL);
    }

    private void deleteDog(int id) {
        setup().when().delete(BASE_URL + "/" + id);
    }

    private MockMvcRequestSpecification setup() {
        return given().standaloneSetup(dogController);
    }
}
