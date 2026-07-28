package ec.solmedia.mooc.infrastructure.bus.event.kafka;

import ec.solmedia.shared.domain.event.bus.DomainEvent;
import ec.solmedia.shared.infrastructure.bus.event.kafka.DomainEventEnvelopeSerializer;
import ec.solmedia.shared.infrastructure.bus.event.kafka.DomainEventKafkaDeserializer;
import ec.solmedia.shared.infrastructure.bus.event.kafka.DomainEventKafkaSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaEventBusConfiguration {

  @Bean
  public ObjectMapper domainEventObjectMapper() {
    return DomainEventMixIn.objectMapper();
  }

  @Bean
  public DomainEventEnvelopeSerializer domainEventEnvelopeSerializer(ObjectMapper domainEventObjectMapper) {
    return new DomainEventEnvelopeSerializer(domainEventObjectMapper);
  }

  @Bean
  public DomainEventKafkaSerializer domainEventKafkaSerializer(
      DomainEventEnvelopeSerializer domainEventEnvelopeSerializer) {
    return new DomainEventKafkaSerializer(domainEventEnvelopeSerializer);
  }

  @Bean
  public ProducerFactory<String, DomainEvent> kafkaProducerFactory(
      KafkaConnectionDetails kafkaConnectionDetails,
      DomainEventKafkaSerializer domainEventKafkaSerializer) {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConnectionDetails.getBootstrapServers());
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DomainEventKafkaSerializer.class);

    return new DefaultKafkaProducerFactory<>(
        config,
        new StringSerializer(),
        domainEventKafkaSerializer
    );
  }

  @Bean
  public KafkaTemplate<String, DomainEvent> kafkaTemplate(
      ProducerFactory<String, DomainEvent> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public ConsumerFactory<String, DomainEvent> domainEventConsumerFactory(
      KafkaConnectionDetails kafkaConnectionDetails,
      ObjectMapper domainEventObjectMapper) {
    Map<String, Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConnectionDetails.getBootstrapServers());
    config.put(ConsumerConfig.GROUP_ID_CONFIG, "solmedia");
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DomainEventKafkaDeserializer.class);
    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    return new DefaultKafkaConsumerFactory<>(
        config,
        new StringDeserializer(),
        new DomainEventKafkaDeserializer(domainEventObjectMapper)
    );
  }

  @Bean
  @Primary
  public ConcurrentKafkaListenerContainerFactory<String, DomainEvent> kafkaListenerContainerFactory(
      ConsumerFactory<String, DomainEvent> domainEventConsumerFactory) {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, DomainEvent>();
    factory.setConsumerFactory(domainEventConsumerFactory);

    return factory;
  }

  @Bean
  public NewTopic courseCreatedTopic(
      @Value("${kafka.topic.course.created.partitions:1}") int partitions,
      @Value("${kafka.topic.course.created.replication-factor:1}") short replicationFactor) {
    return new NewTopic("course.created", partitions, replicationFactor);
  }
}
