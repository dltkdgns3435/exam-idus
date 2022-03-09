package shlee.exam.idus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws Exception {
        if (isArmMac()) {
            redisServer = new RedisServer(getRedisFileForArmMac(),redisPort);
        } else {
            redisServer = new RedisServer(redisPort);
        }
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
    private boolean isArmMac() {
        return System.getProperty("os.arch").equals("aarch64") &&
                System.getProperty("os.name").equals("Mac OS X");
    }

    private File getRedisFileForArmMac() throws IOException {
        return new ClassPathResource("redis/redis-server-m1-mac").getFile();
    }
}
