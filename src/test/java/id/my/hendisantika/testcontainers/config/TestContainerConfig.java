package id.my.hendisantika.testcontainers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.testcontainers.containers.wait.strategy.Wait;
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

    @Bean
    // This annotation is used to indicate that this bean will not be re-created if the application
    // restarts due to spring-dev-tools.
    // @org.springframework.boot.devtools.restart.RestartScope
    // By default, ServiceConnection will create all applicable connection details beans
    // for a given Container.
    // For example, a PostgreSQLContainer will create both JdbcConnectionDetails and R2dbcConnectionDetails.
    @ServiceConnection(type = JdbcConnectionDetails.class)
    public PostgresContainer postgresContainer() {

        final long memoryInBytes = 64L * 1024L * 1024L;
        final long memorySwapInBytes = 128L * 1024L * 1024L;

        return new PostgresContainer(POSTGRES_IMAGE)
                .waitingFor(Wait.forLogMessage(".*PostgreSQL init process complete;.*\\n", 1))
                // The Reusable Containers feature keeps the same containers running between test sessions
                .withReuse(true)
                .withCreateContainerCmdModifier(cmd -> {
                    cmd.withName("postgres");
                    cmd.getHostConfig()
                            .withMemory(memoryInBytes)
                            .withMemorySwap(memorySwapInBytes);
                });
    }
}
