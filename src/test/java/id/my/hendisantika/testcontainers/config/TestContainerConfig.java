package id.my.hendisantika.testcontainers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.testcontainers.utility.DockerImageName;

/**
 * Created by IntelliJ IDEA.
 * Project : testcontainers
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 28/06/24
 * Time: 06.32
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class TestContainerConfig {

    public static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgres:17beta1-alpine3.20");
    public static final DockerImageName REDIS_IMAGE = DockerImageName.parse("redis:7.2.5-alpine3.20");
    public static final DockerImageName KAFKA_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:7.6.1");

    @Autowired
    private ResourceLoader resourceLoader;
}
