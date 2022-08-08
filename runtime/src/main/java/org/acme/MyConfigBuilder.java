package org.acme;

import io.quarkus.runtime.configuration.ConfigBuilder;
import io.smallrye.config.SmallRyeConfigBuilder;
import org.acme.interceptor.MyInterceptor;

public class MyConfigBuilder implements ConfigBuilder {

    @Override
    public SmallRyeConfigBuilder configBuilder(SmallRyeConfigBuilder builder) {
        return builder.withInterceptors(new MyInterceptor());
    }

    @Override
    public int priority() {
        return ConfigBuilder.super.priority();
    }
}
