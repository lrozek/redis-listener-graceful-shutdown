# redis-listener-graceful-shutdown

To build and run the app

```shell
docker-compose down -v && docker-compose up --build
```

When app is running execute following:

```shell
docker-compose exec redis redis-cli XADD mystream \* sensorId 1234 temperature 19.8 && docker-compose stop consumer
```


As a result you should see in the logs following entries:
```
2022-02-15 18:04:34.883  INFO 1 --- [cTaskExecutor-1] p.l.r.l.g.s.l.RedisStreamListener        : received following stream entry: MapBackedRecord{recordId=1644948274878-0, kvMap={sensorId=1234, temperature=19.8}}
2022-02-15 18:04:34.885  INFO 1 --- [cTaskExecutor-1] p.l.r.l.g.s.l.RedisStreamListener        : will sleep for 15s
2022-02-15 18:04:35.406 DEBUG 1 --- [ionShutdownHook] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@503f91c3, started on Tue Feb 15 18:03:35 UTC 2022
2022-02-15 18:04:35.412 DEBUG 1 --- [ionShutdownHook] o.s.c.support.DefaultLifecycleProcessor  : Stopping beans in phase 2147483647
2022-02-15 18:04:35.414 DEBUG 1 --- [ionShutdownHook] o.s.c.support.DefaultLifecycleProcessor  : Bean 'listener' completed its stop procedure
2022-02-15 18:04:35.417 DEBUG 1 --- [ionShutdownHook] o.s.d.r.l.RedisMessageListenerContainer  : Stopped RedisMessageListenerContainer
```

In the output there is a missing log entry produced by https://github.com/lrozek/redis-listener-graceful-shutdown/blob/main/src/main/java/pl/lrozek/redis/listener/graceful/shutdown/listener/RedisStreamListener.java#L27 meaning that listener did not have a chance to finish its processing. RedisMessageListenerContainer should wait with some configurable timeout until listener finishes processing.

Similiar functionality is offered for instance for rabbitmq listener, by `SimpleMessageListenerContainer` at https://github.com/spring-projects/spring-amqp/blob/main/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/listener/SimpleMessageListenerContainer.java#L634
