package uk.bit1.email;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.kafka.annotation.KafkaListener;

@ExtendWith(OutputCaptureExtension.class)
class EmailMessageConsumerTests {

    private final EmailMessageConsumer consumer = new EmailMessageConsumer();

    @Test
    void consumeLogsKafkaRecord(CapturedOutput output) {
        ConsumerRecord<String, String> record = new ConsumerRecord<>(
                "demo-email-topic",
                0,
                42L,
                "email-123",
                "{\"to\":\"demo@example.com\",\"subject\":\"Hello from Kafka\"}"
        );

        consumer.consume(record);

        String logs = output.getAll();
        assertTrue(logs.contains("Consumed email message from topic=demo-email-topic"));
        assertTrue(logs.contains("partition=0"));
        assertTrue(logs.contains("offset=42"));
        assertTrue(logs.contains("key=email-123"));
        assertTrue(logs.contains("value={\"to\":\"demo@example.com\",\"subject\":\"Hello from Kafka\"}"));
    }

    @Test
    void consumeUsesConfiguredKafkaListenerTopicAndGroup() throws NoSuchMethodException {
        Method consume = EmailMessageConsumer.class.getMethod("consume", ConsumerRecord.class);

        KafkaListener listener = consume.getAnnotation(KafkaListener.class);

        assertArrayEquals(new String[]{"${app.kafka.email-topic}"}, listener.topics());
        assertEquals("${spring.kafka.consumer.group-id}", listener.groupId());
    }
}
