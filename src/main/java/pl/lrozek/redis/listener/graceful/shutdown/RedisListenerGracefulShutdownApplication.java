package pl.lrozek.redis.listener.graceful.shutdown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisListenerGracefulShutdownApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisListenerGracefulShutdownApplication.class, args);
	}

}
