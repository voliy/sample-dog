package voliy.samples.dog.proxyexample.cglib;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class MyAdvisor implements MethodBeforeAdvice {
    private String someProperty;

    public String getSomeProperty() {
        return someProperty;
    }

    public void setSomeProperty(String someProperty) {
        this.someProperty = someProperty;
    }

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("CGLIB-based proxy sample before: " + someProperty);
    }
}
