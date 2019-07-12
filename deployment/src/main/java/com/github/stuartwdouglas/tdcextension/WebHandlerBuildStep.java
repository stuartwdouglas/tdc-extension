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
import io.quarkus.deployment.builditem.substrate.ReflectiveHierarchyBuildItem;
import io.quarkus.undertow.deployment.ServletBuildItem;

public class WebHandlerBuildStep {

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem("tdc-extension");
    }

    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    void sayHello(WehHandlerRecoder recorder,
                  CombinedIndexBuildItem index,
                  BuildProducer<ServletBuildItem> servlets,
                  BuildProducer<ReflectiveClassBuildItem> reflection,
                  BuildProducer<ReflectiveHierarchyBuildItem> heiracy,
                  BuildProducer<AdditionalBeanBuildItem> beans) {
        DotName webHandler = DotName.createSimple(WebHandler.class.getName());

        for (AnnotationInstance i : index.getIndex().getAnnotations(webHandler)) {

            MethodInfo method = i.target().asMethod();
            String className = method.declaringClass().name().toString();

            beans.produce(AdditionalBeanBuildItem.unremovableOf(className));

            reflection.produce(new ReflectiveClassBuildItem(true, false, className));
            heiracy.produce(new ReflectiveHierarchyBuildItem(method.returnType()));

            servlets.produce(ServletBuildItem.builder(className + "." + method.name(), WebHandlerServlet.class.getName())
                    .setInstanceFactory(recorder.instanceFactory(className, method.name()))
                    .addMapping(i.value("path").asString())
                    .build());

        }
    }


}
