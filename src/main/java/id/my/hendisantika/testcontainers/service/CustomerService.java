package id.my.hendisantika.testcontainers.service;

import id.my.hendisantika.testcontainers.config.RedisConfig;
import id.my.hendisantika.testcontainers.dto.CustomerDTO;
import id.my.hendisantika.testcontainers.publisher.CustomerEventPublisher;
import id.my.hendisantika.testcontainers.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : testcontainers
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 27/06/24
 * Time: 16.54
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerEventPublisher publisher;
    private final Environment env;

    public List<CustomerDTO> findAll() {
        return customerRepository.findAll().stream().map(customer ->
                new CustomerDTO(customer.getName(), customer.getId())).toList();
    }

    @Cacheable(RedisConfig.CUSTOMER_CACHE)
    public List<CustomerDTO> findByName(String name) {
        log.info("------ Hitting database & not using cache! ------ ");
        return customerRepository.findByNameIgnoreCase(name).stream().map(customer ->
                new CustomerDTO(customer.getName(), customer.getId())).toList();
    }
}
