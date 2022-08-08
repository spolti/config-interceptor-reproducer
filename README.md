# SmallryeConfig Interceptor Reproducer

It is a small reproducer that invokes the ConfigSourceInterceptor at static_init and runtime when using Quarkus extension
that registers the Interceptor with a custom ConfigBuilder with the custom interceptor.

Build the app:
```java
mvn clean install
```

Run and check the logs:
```java
java -jar quarkus-app/target/quarkus-app/quarkus-run.jar
__  ____  __  _____   ___  __ ____  ______
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/
2022-08-08 14:53:16,523 INFO  [org.acm.int.MyInterceptor] (main) Interceptor called - no cache... setting new value for my.sys.prop -- value: my-new-value
2022-08-08 14:53:16,561 INFO  [org.acm.AppLifecycle] (main) The application is starting
2022-08-08 14:53:16,624 INFO  [io.quarkus] (main) config-interceptor-reproducer-quarkus-app 1.0.0-SNAPSHOT on JVM (powered by Quarkus 2.11.1.Final) started in 0.630s. Listening on: http://0.0.0.0:80802022-08-08 14:53:16,626 INFO  [io.quarkus] (main) Profile prod activated.
2022-08-08 14:53:16,627 INFO  [io.quarkus] (main) Installed features: [cdi, reproducer-interceptor-extension, resteasy, smallrye-context-propagation, vertx]
```

Call the app after it is started:
```java
curl http://localhost:8080/test
```

Check the logs that you will see the interceptor being called again:
```java
2022-08-08 14:53:23,550 INFO  [org.acm.int.MyInterceptor] (executor-thread-0) Interceptor called - cache my-new-value
        
# After the first request, the iterceptor is not called again.
2022-08-08 14:53:23,552 INFO  [org.acm.App] (executor-thread-0) request............. my-new-value
```