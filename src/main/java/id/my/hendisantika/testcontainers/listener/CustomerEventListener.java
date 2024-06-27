package id.my.hendisantika.testcontainers.listener;

import id.my.hendisantika.testcontainers.dto.CustomerDTO;
import id.my.hendisantika.testcontainers.entity.Customer;
import id.my.hendisantika.testcontainers.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * Project : testcontainers
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 27/06/24
 * Time: 16.53
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
class CustomerEventListener {

    private final CustomerRepository customerRepository;

    @KafkaListener(topics = "customers")
    public void handleCustomerCreatedEvent(CustomerDTO customerDTO) {
        log.info("Customer event received from customer topic");
        Customer customer = new Customer();
        customer.setId(customerDTO.id());
        customer.setName(customerDTO.name());
        customerRepository.save(customer);
    }
}
