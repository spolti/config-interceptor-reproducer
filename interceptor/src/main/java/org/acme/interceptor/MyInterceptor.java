package org.acme.interceptor;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Iterator;

import io.smallrye.config.ConfigSourceInterceptor;
import io.smallrye.config.ConfigSourceInterceptorContext;
import io.smallrye.config.ConfigValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this class extends the ConfigSource in order to override the system properties
 * with the discovered SWUrl
 */
public class MyInterceptor implements ConfigSourceInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    // workaround to not be called twice, static init and runtime
    private HashMap<String, String> cache = new HashMap<>();

    @Override
    public ConfigValue getValue(ConfigSourceInterceptorContext context, String s) {

        ConfigValue configValue = context.proceed(s);
        // hardcoded with static property value, the current use case for this would expect a system property value that matches
        // specific string
        if (null != configValue && configValue.getValue().equals("my-old-value")) {

            // workaround to avoid having the next methods called twice ( in this reproducer the method to be called is not
            // much expensive, on my use case it will query kubernetes resources based on the info by the user.
            // it would be enough to get this running only at static_init and at runtime the value from the workaround
            // cache is returned.
            if (cache.containsKey(configValue.getName())) {
                LOGGER.info("Interceptor called - cached " + cache.get(configValue.getName()));
                return configValue.withValue(cache.get(configValue.getName()));
            }
            String value = "my-new-value";
            LOGGER.info("Interceptor called - no cache... setting new value for " + configValue.getName() + " -- value: " +value);
            cache.put(configValue.getName(), value);
            return configValue.withValue(value);
        }

        return configValue;
    }

    @Override
    public Iterator<String> iterateNames(ConfigSourceInterceptorContext context) {
        return ConfigSourceInterceptor.super.iterateNames(context);
    }

    @Override
    public Iterator<ConfigValue> iterateValues(ConfigSourceInterceptorContext context) {
        return ConfigSourceInterceptor.super.iterateValues(context);
    }
}
