package com.os.search_service.config;

import com.os.search_service.document.Product;
import com.os.search_service.dto.DeleteProductRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaAddress;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    // ürün ekleme consumer
    @Bean
    public ConsumerFactory<String, Product> consumerFactory() {
        JsonDeserializer<Product> jsonDeserializer = new JsonDeserializer<>(Product.class);
        jsonDeserializer.addTrustedPackages("*"); // Specify trusted packages
        jsonDeserializer.setUseTypeHeaders(false); // If type headers are not used

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName()); // Use class name

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Product> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Product> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // ürün silme consumer
    @Bean
    public ConsumerFactory<String, DeleteProductRequest> deleteProductConsumerFactory() {
        JsonDeserializer<DeleteProductRequest> jsonDeserializer = new JsonDeserializer<>(DeleteProductRequest.class);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setUseTypeHeaders(false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId + "-delete-product");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeleteProductRequest> kafkaListenerDeleteProductContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DeleteProductRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deleteProductConsumerFactory());
        return factory;
    }
    // ürün güncelleme consumer
    @Bean
    public ConsumerFactory<String, Product> updateProductConsumerFactory() {
        JsonDeserializer<Product> jsonDeserializer = new JsonDeserializer<>(Product.class);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setUseTypeHeaders(false);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId + "-update-product");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Product> kafkaListenerUpdateProductContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Product> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(updateProductConsumerFactory());
        return factory;
    }


}
