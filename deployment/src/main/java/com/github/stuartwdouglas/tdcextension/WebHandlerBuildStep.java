package com.github.stuartwdouglas.tdcextension;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.DotName;
import org.jboss.jandex.MethodInfo;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.substrate.ReflectiveClassBuildItem;
import io.quarkus.undertow.deployment.ServletBuildItem;
import io.undertow.servlet.api.InstanceFactory;

public class WebHandlerBuildStep {

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem("tdc-extension");
    }

    @Record(ExecutionTime.STATIC_INIT)
    @BuildStep
    public void handleWebHandlers(CombinedIndexBuildItem indexBuildItem,
                                  BuildProducer<ServletBuildItem> servletProducer,
                                  BuildProducer<AdditionalBeanBuildItem> beanProducer,
                                  BuildProducer<ReflectiveClassBuildItem> reflecticeClassProducer,
                                  WebHandlerTemplate template,
                                  WebHandlerConfig webHandlerConfig) throws Exception {
        for (AnnotationInstance annotation : indexBuildItem.getIndex().getAnnotations(DotName.createSimple(WebHandler.class.getName()))) {
            MethodInfo method = annotation.target().asMethod();
            String path = annotation.value("path").asString();
            String className = method.declaringClass().toString();
            InstanceFactory<WebHandlerServlet> instance = template.createInstance(className, method.name());

            beanProducer.produce(AdditionalBeanBuildItem.unremovableOf(className));

            reflecticeClassProducer.produce(new ReflectiveClassBuildItem(true, true, className, method.returnType().asClassType().toString()));


            ServletBuildItem servletBuildItem = ServletBuildItem.builder(className + "-" + method, WebHandlerServlet.class.getName())
                    .setInstanceFactory(instance)
                    .addMapping(webHandlerConfig.path + path).build();
            servletProducer.produce(servletBuildItem);
        }
    }
}
