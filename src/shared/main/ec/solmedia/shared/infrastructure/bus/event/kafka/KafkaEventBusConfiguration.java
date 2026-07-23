package ec.solmedia.shared.infrastructure.bus.event.kafka;

import ec.solmedia.shared.domain.event.bus.DomainEvent;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaEventBusConfiguration {

  @Bean
  public ProducerFactory<String, DomainEvent> kafkaProducerFactory(
      @Value("${spring.kafka.bootstrap-servers:localhost:9092}") String bootstrapServers) {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DomainEventKafkaSerializer.class);

    return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), new DomainEventKafkaSerializer());
  }

  @Bean
  public KafkaTemplate<String, DomainEvent> kafkaTemplate(
      ProducerFactory<String, DomainEvent> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }
}
