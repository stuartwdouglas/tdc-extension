package com.github.stuartwdouglas.tdcextension;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WebHandler {

    String path();
}
