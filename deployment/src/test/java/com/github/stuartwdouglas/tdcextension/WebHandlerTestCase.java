package com.github.stuartwdouglas.tdcextension;

import java.util.function.Supplier;

import org.hamcrest.Matchers;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;
import io.restassured.RestAssured;

public class WebHandlerTestCase {

    @RegisterExtension
    static QuarkusUnitTest test = new QuarkusUnitTest()
            .setArchiveProducer(new Supplier<JavaArchive>() {
                @Override
                public JavaArchive get() {
                    JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                            .addClasses(TestData.class, TestHandler.class);
                    return jar;
                }
            });

    @Test
    public void testWebHandler() {
        RestAssured.when().get("/data")
                .then().body("data", Matchers.equalTo("test data"));
    }

}
