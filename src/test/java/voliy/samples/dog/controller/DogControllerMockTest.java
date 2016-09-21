package voliy.samples.dog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import voliy.samples.dog.dao.DogDao;
import voliy.samples.dog.model.Dog;
import voliy.samples.dog.service.DogService;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.testng.AssertJUnit.assertNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(locations = {"classpath:dao-context.xml"})
public class DogControllerMockTest extends AbstractTransactionalTestNGSpringContextTests {
    private static final String BASE_URL = "/dog";

    private DogController dogController;
    private ObjectMapper objectMapper;

    @Autowired private DogDao dogDao;
    @Autowired private SessionFactory sessionFactory;

    @BeforeMethod private void init() {
        dogController = new DogController(new DogService(dogDao));
        objectMapper = new ObjectMapper();
    }

    @Test public void savesAndLoadsDog() throws Exception {
        Dog expected = addDog(Dog.random());
        flushAndClear();

        Dog actual = getDog(expected.getId());
        assertReflectionEquals(expected, actual);
    }

    @Test public void addsDogAndUpdatesItsFields() throws Exception {
        Dog dog = addDog(Dog.random());
        flushAndClear();

        int dogId = dog.getId();
        Dog expected = Dog.random();
        expected.setId(dogId);
        updateDog(expected);
        flushAndClear();

        Dog actual = getDog(dogId);
        assertReflectionEquals(expected, actual);
    }

    @Test public void savesAndDeletesDog() throws Exception {
        Dog dog = addDog(Dog.random());
        flushAndClear();

        int dogId = dog.getId();
        deleteDog(dogId);
        flushAndClear();

        dog = getDog(dogId);
        assertNull(dog);
    }

//    @Test public void addsDogsAndLoadsAllOfThem() throws Exception {
//        Dog firstDog = addDog(Dog.random());
//        Dog secondDog = addDog(Dog.random());
//        flushAndClear();
//
//        List<Dog> allDogs = loadAllDogs();
//        assertReflectionEquals(Arrays.asList(secondDog, firstDog), Arrays.asList(allDogs.get(0), allDogs.get(1)),
//                LENIENT_ORDER);
//    }

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

//    @SuppressWarnings("unchecked")
//    private List<Dog> loadAllDogs() throws Exception {
//        String response = setup().when().get(BASE_URL).andReturn().getMvcResult().getResponse()
//                .getContentAsString();
//        if (StringUtils.isEmpty(response)) return null;
//        return objectMapper.readValue(response, List.class);
//    }

    private void updateDog(Dog dog) throws Exception {
        setup().contentType(ContentType.JSON).body(dog).when().put(BASE_URL);
    }

    private void deleteDog(int id) {
        setup().when().delete(BASE_URL + "/" + id);
    }

    private MockMvcRequestSpecification setup() {
        return given().standaloneSetup(dogController);
    }

    private void flushAndClear() {
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }
}
