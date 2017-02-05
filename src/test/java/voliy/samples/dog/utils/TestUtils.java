package voliy.samples.dog.utils;

import voliy.samples.dog.model.Dog;

import java.util.Collection;
import java.util.Optional;

import static org.testng.Assert.assertTrue;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class TestUtils {
    public static void assertContainsDog(Collection<Dog> dogs, Dog dog) {
        Optional<Dog> dogOptional = dogs.stream().filter(d -> d.getId().equals(dog.getId())).findFirst();
        assertTrue(dogOptional.isPresent());
        assertReflectionEquals(dog, dogOptional.get());
    }
}
