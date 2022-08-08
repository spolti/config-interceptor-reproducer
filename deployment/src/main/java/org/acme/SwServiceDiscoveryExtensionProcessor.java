package org.acme;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.RunTimeConfigBuilderBuildItem;

class SwServiceDiscoveryExtensionProcessor {

    private static final String FEATURE = "reproducer-interceptor-extension";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void staticInitConfigBuilderProducer(BuildProducer<RunTimeConfigBuilderBuildItem> rcb) {
        System.out.println("Build Step called...");
        rcb.produce(new RunTimeConfigBuilderBuildItem(MyConfigBuilder.class.getName()));
    }
}
