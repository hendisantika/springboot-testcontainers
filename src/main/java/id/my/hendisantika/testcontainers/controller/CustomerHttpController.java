package id.my.hendisantika.testcontainers.controller;

import id.my.hendisantika.testcontainers.dto.CustomerDTO;
import id.my.hendisantika.testcontainers.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : testcontainers
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 27/06/24
 * Time: 16.56
 * To change this template use File | Settings | File Templates.
 */
@Controller
@ResponseBody
@RequiredArgsConstructor
@Slf4j
public class CustomerHttpController {

    private final CustomerService customerService;

    @GetMapping(value = "/customers", produces = "application/json")
    public List<CustomerDTO> customers() {
        List<CustomerDTO> customers = this.customerService.findAll();
        log.info("Found {} customers", customers.size());
        return customers;
    }
}
