package com.github.stuartwdouglas.tdcextension;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.enterprise.inject.spi.CDI;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebHandlerServlet extends HttpServlet {

    private final Method method;

    public WebHandlerServlet(Method method) {
        this.method = method;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Object instace = CDI.current().select(method.getDeclaringClass()).get();
        try {
            Object result = method.invoke(instace);
            JsonbBuilder.create().toJson(result, resp.getWriter());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
