package com.github.stuartwdouglas.tdcextension;

public class SampleWebHandler {

    @WebHandler(path = "/foo")
    public Person person() {
        Person p = new Person();
        p.setFirst("Stuart");
        p.setLast("Douglas");
        return p;
    }

}
