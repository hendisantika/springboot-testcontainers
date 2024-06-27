package id.my.hendisantika.testcontainers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringbootTestcontainersApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTestcontainersApplication.class, args);
    }

}
