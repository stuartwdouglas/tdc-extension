package com.test;

import com.github.stuartwdouglas.tdcextension.WebHandler;

public class HandlerClass {

    @WebHandler(path = "/person")
    public Person getPerson() {
        Person p = new Person();
        p.setFirst("Stuart");
        p.setLast("Douglas");
        return p;
    }
}
