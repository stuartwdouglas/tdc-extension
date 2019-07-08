package com.github.stuartwdouglas.tdcextension;

import java.lang.reflect.Method;

import io.quarkus.runtime.annotations.Template;
import io.undertow.servlet.api.InstanceFactory;
import io.undertow.servlet.api.InstanceHandle;
import io.undertow.servlet.util.ImmediateInstanceHandle;

@Template
public class WebHandlerTemplate {

    public InstanceFactory<WebHandlerServlet> createInstance(String handlerClass, String methodName) throws Exception {

        return new InstanceFactory<WebHandlerServlet>() {
            @Override
            public InstanceHandle<WebHandlerServlet> createInstance() throws InstantiationException {
                try {
                    Class<?> handler = Class.forName(handlerClass, false, Thread.currentThread().getContextClassLoader());
                    Method method = handler.getMethod(methodName);
                    System.out.println("Discovered handler method" + method);
                    return new ImmediateInstanceHandle(new WebHandlerServlet(method));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
