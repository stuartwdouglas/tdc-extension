package com.github.stuartwdouglas.tdcextension;

public class TestHandler {

    @WebHandler(path = "/data")
    public TestData data() {
        TestData data = new TestData();
        data.data = "test data";
        return data;
    }

}
