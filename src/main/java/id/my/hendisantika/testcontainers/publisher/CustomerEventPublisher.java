package id.my.hendisantika.testcontainers.publisher;

import id.my.hendisantika.testcontainers.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : testcontainers
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 27/06/24
 * Time: 16.52
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCustomerCreatedEvent(CustomerDTO data) {
        kafkaTemplate.send("customers", data);
        log.info("CustomerCreatedEvent sent to products topic");
    }
}
