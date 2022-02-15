package pl.lrozek.redis.listener.graceful.shutdown.configuration;

import static org.springframework.data.redis.connection.stream.StreamOffset.fromStart;
import static org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

@Configuration
public class RedisListenerConfiguration {

    @Bean
    public StreamMessageListenerContainer<String, MapRecord<String, String, String>> listener(RedisConnectionFactory connectionFactory, StreamListener<String, MapRecord<String, String, String>> streamListener) {
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container = StreamMessageListenerContainer.create(connectionFactory, builder().build());

        container.receive(fromStart("mystream"), streamListener);
        container.start();
        return container;
    }

}
