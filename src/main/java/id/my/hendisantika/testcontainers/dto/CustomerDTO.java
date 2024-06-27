package id.my.hendisantika.testcontainers.dto;

import java.io.Serializable;

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
public record CustomerDTO(String name, Integer id)
        implements Serializable {
}
