package com.github.stuartwdouglas.tdcextension;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(phase = ConfigPhase.BUILD_TIME)
public class WebHandlerConfig {

    /**
     * The context path that all web handlers are registered under
     */
    @ConfigItem(defaultValue = "")
    String path;
}
