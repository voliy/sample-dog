package foo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Main {
    // -javaagent:D:/projects/trainings/sample-dog/target/sample-dog-1.0.0/WEB-INF/lib/spring-instrument-4.3.2.RELEASE.jar
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml", Main.class);

        StubEntitlementCalculationService entitlementCalculationService
                = (StubEntitlementCalculationService) ctx.getBean("entitlementCalculationService");

        // the profiling aspect is 'woven' around this method execution
        entitlementCalculationService.test();
    }
}