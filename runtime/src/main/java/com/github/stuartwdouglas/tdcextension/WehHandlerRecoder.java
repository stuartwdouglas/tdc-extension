package com.github.stuartwdouglas.tdcextension;

import java.lang.reflect.Method;

import javax.servlet.Servlet;

import io.quarkus.runtime.annotations.Recorder;
import io.undertow.servlet.api.InstanceFactory;
import io.undertow.servlet.api.InstanceHandle;
import io.undertow.servlet.util.ImmediateInstanceHandle;

@Recorder
public class WehHandlerRecoder {


    public InstanceFactory<Servlet> instanceFactory(String className, String methodName) {
        return new InstanceFactory<Servlet>() {
            @Override
            public InstanceHandle<Servlet> createInstance() throws InstantiationException {
                try {
                    Class<?> clazz = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
                    Method m = clazz.getDeclaredMethod(methodName);
                    return new ImmediateInstanceHandle<>(new WebHandlerServlet(m));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

}
