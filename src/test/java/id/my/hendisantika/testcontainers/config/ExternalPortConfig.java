package id.my.hendisantika.testcontainers.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;

/**
 * Created by IntelliJ IDEA.
 * Project : testcontainers
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 27/06/24
 * Time: 17.03
 * To change this template use File | Settings | File Templates.
 */
//From the host's perspective Testcontainers actually exposes .exposePort() on a random free port.
// it-s possible to define a fixed port, but
// This is by design, to avoid port collisions that may arise with locally running software or in between parallel test runs.
@Configuration
public class ExternalPortConfig {

    @Autowired
    private GenericContainer pythonContainer;

    @PostConstruct
    public void initPort() {
        System.setProperty("EXTERNAL_CUSTOMER_SERVICE_PORT", String.valueOf(
                pythonContainer.getMappedPort(5000)
        ));
    }
}
