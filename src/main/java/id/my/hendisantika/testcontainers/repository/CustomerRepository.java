package id.my.hendisantika.testcontainers.repository;

import id.my.hendisantika.testcontainers.entity.Customer;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : testcontainers
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 27/06/24
 * Time: 16.51
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface CustomerRepository extends ListCrudRepository<Customer, Integer> {
    List<Customer> findByNameIgnoreCase(String name);
}
