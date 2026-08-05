package uk.bit1.email;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailMessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmailMessageConsumer.class);

    @KafkaListener(topics = "${app.kafka.email-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, String> record) {
        log.info(
                "Consumed email message from topic={}, partition={}, offset={}, key={}, value={}",
                record.topic(),
                record.partition(),
                record.offset(),
                record.key(),
                record.value()
        );
    }
}
