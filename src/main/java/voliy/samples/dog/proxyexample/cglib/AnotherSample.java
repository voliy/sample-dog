package voliy.samples.dog.proxyexample.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnotherSample {
    public static void main(String[] args) {
        List<String> values = new ArrayList<>();
        values.add("First");
        values.add("Second");

        List<String> interfaceProxy = (List<String>) Enhancer.create(List.class, new SampleInterceptor(values));
        for (int i = 0; i < 3; i++) {
            System.out.println(interfaceProxy.get(i));
        }

        System.out.println("----------");

        List<String> classProxy = (List<String>) Enhancer.create(ArrayList.class, new SampleInterceptor(values));
        for (int i = 0; i < 3; i++) {
            System.out.println(classProxy.get(i));
        }
    }

    private static class SampleInterceptor implements MethodInterceptor {
        private List<String> array;

        SampleInterceptor(List<String> array) {
            this.array = array;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if ("get".equals(method.getName()) && ((Integer) args[0]) == 2) {
                return "Third";
            }
            return proxy.invoke(array, args);
        }
    }
}
