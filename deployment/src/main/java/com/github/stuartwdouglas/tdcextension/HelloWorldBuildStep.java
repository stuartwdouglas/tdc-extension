package com.github.stuartwdouglas.tdcextension;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;

public class HelloWorldBuildStep {

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem("tdc-extension");
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    public void hello(HelloWorldTemplate template) {
        template.helloWorld();
    }
}
