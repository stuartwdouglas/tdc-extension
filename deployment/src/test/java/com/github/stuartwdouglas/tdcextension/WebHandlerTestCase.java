package com.github.stuartwdouglas.tdcextension;

import static org.hamcrest.Matchers.equalTo;

import java.util.function.Supplier;

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
                    return ShrinkWrap.create(JavaArchive.class)
                            .addClasses(Person.class, SampleWebHandler.class);
                }
            });


    @Test
    public void testWebHandler() {
        RestAssured.when().get("/foo")
                .then().assertThat().content("first", equalTo("Stuart"))
                .content("last", equalTo("Douglas"));
    }
}
