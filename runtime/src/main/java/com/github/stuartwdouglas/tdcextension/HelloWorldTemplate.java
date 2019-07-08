package com.github.stuartwdouglas.tdcextension;

import io.quarkus.runtime.annotations.Template;

@Template
public class HelloWorldTemplate {

    public void helloWorld() {
        System.out.println("Hello World");
    }
}
