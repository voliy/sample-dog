package voliy.samples.dog.util;

import java.util.Random;

import static io.qala.datagen.RandomShortApi.positiveLong;

public class DogUtils {
    public static double positiveDouble() {
        return positiveLong() * new Random().nextDouble();
    }
}
