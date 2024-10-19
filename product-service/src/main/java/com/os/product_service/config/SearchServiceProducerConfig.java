package com.os.product_service.config;

import com.os.product_service.dto.request.product.CreateProductRequest;
import com.os.product_service.dto.request.product.DeleteProductRequest;
import com.os.product_service.dto.response.product.CreateProductResponse;
import com.os.product_service.dto.response.product.UpdateProductResponse;
import com.os.product_service.model.Product;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class SearchServiceProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaAddress;

    private <T> ProducerFactory<String, T> producerFactory(Class<T> valueType) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, CreateProductResponse> createProductKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(CreateProductResponse.class));
    }

    @Bean
    public KafkaTemplate<String, DeleteProductRequest> deleteProductKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(DeleteProductRequest.class));
    }

    @Bean
    public KafkaTemplate<String, UpdateProductResponse> updateProductKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(UpdateProductResponse.class));
    }



}
