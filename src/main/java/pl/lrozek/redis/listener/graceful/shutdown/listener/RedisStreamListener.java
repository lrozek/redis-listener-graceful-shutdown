package pl.lrozek.redis.listener.graceful.shutdown.listener;

import static java.time.Duration.ofSeconds;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedisStreamListener implements StreamListener<String, MapRecord<String, String, String>> {

    private int sleepDuration = 15;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        log.info("received following stream entry: {}", message);
        sleep();
    }

    private void sleep() {
        try {
            log.info("will sleep for {}s", sleepDuration);
            Thread.sleep(ofSeconds(sleepDuration).toMillis());
            log.info("has slept for {}s", sleepDuration);
        } catch (InterruptedException e) {
            log.error("interrupted", e);
        }
    }

}
