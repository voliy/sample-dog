package voliy.samples.dog.model;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static io.qala.datagen.RandomShortApi.*;
import static io.qala.datagen.RandomValue.between;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DogTest {
    private static Validator validator;

    @BeforeClass private static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test public void successfullyValidatesDog() {
        assertTrue(validator.validate(Dog.random()).isEmpty());
    }

    @Test public void successfullyValidatesDog_withNameSizeEqualTo1() {
        Dog dog = Dog.random();
        dog.setName(alphanumeric(1));
        assertTrue(validator.validate(dog).isEmpty());
    }

    @Test public void successfullyValidatesDog_withNameSizeEqualTo100() {
        Dog dog = Dog.random();
        dog.setName(alphanumeric(100));
        assertTrue(validator.validate(dog).isEmpty());
    }

    @Test public void errorsWhenValidatesDog_withNameSizeEqualTo101() {
        Dog dog = Dog.random();
        dog.setName(alphanumeric(101));
        assertHasViolation(validator.validate(dog), "Dog name size must be between 1 and 100");
    }

    @Test public void errorsWhenValidatesDog_withNameSizeMoreThan100() {
        Dog dog = Dog.random();
        dog.setName(alphanumeric(101, 1000));
        assertHasViolation(validator.validate(dog), "Dog name size must be between 1 and 100");
    }

    @Test public void errorsWhenValidatesDog_withNullOrEmptyName() {
        Dog dog = Dog.random();
        dog.setName(nullOrBlank());
        assertHasViolation(validator.validate(dog), "may not be empty");
    }

    @Test public void successfullyValidatesDog_withBirthDateBeforeNow() {
        Dog dog = Dog.random();
        dog.setBirthDate(between(Long.MIN_VALUE, new Date().getTime()).date());
        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        assertTrue(violations.isEmpty());
    }

    @Test public void successfullyValidatesDog_withNullBirthDate() {
        Dog dog = Dog.random();
        dog.setBirthDate(null);
        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        assertTrue(violations.isEmpty());
    }

    @Test public void errorsWhenValidatesDog_withBirthDateAfterNow() {
        Dog dog = Dog.random();
        dog.setBirthDate(between(new Date().getTime() + 3000, Long.MAX_VALUE).date());
        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        assertEquals(violations.size(), 1);
    }

    @Test public void successfullyValidatesDog_withPositiveHeight() {
        Dog dog = Dog.random();
        dog.setHeight(positiveDouble());
        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        assertTrue(violations.isEmpty());
    }

    @Test public void errorsWhenValidatesDog_withNullOrZeroHeight() {
        Dog dog = Dog.random();
        dog.setHeight(nullOr(0d));
        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        assertEquals(violations.size(), 1);
    }

    @Test public void successfullyValidatesDog_withPositiveWeight() {
        Dog dog = Dog.random();
        dog.setWeight(positiveDouble());
        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        assertTrue(violations.isEmpty());
    }

    @Test public void errorsWhenValidatesDog_withNullOrZeroWeight() {
        Dog dog = Dog.random();
        dog.setWeight(sample(0d, null));
        Set<ConstraintViolation<Dog>> violations = validator.validate(dog);
        assertEquals(violations.size(), 1);
    }

    private static void assertHasViolation(Set<ConstraintViolation<Dog>> violations, String message) {
        Set<String> messages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        assertTrue(messages.contains(message));
    }
}
