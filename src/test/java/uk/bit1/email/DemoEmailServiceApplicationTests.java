package uk.bit1.email;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.kafka.listener.auto-startup=false")
class DemoEmailServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
