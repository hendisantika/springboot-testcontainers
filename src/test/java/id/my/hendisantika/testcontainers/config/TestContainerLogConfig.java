package id.my.hendisantika.testcontainers.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

/**
 * Created by IntelliJ IDEA.
 * Project : testcontainers
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 27/06/24
 * Time: 17.04
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@Service
public class TestContainerLogConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestContainerLogConfig.class);

    static {
        GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse("redis:7.2.5-alpine3.20")).withExposedPorts(6379);
        redis.start();
    }

    @Autowired
    private PostgreSQLContainer postgresContainer;
    @Autowired
    private KafkaContainer kafkaContainer;
    @Autowired
    private GenericContainer pythonContainer;

    @PostConstruct
    public void init() {

        // Attaching the log of the Containers we create to the log of the Spring-Boot app.
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(LOGGER);
        logConsumer.withPrefix("TC-LOG--->");

        postgresContainer.followOutput(logConsumer);
        kafkaContainer.followOutput(logConsumer);
        pythonContainer.followOutput(logConsumer);

    }
}
