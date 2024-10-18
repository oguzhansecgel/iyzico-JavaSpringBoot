package com.os.product_service.kafka;

import com.os.product_service.dto.request.product.CreateProductRequest;
import com.os.product_service.dto.request.product.DeleteProductRequest;
import com.os.product_service.dto.response.product.CreateProductResponse;
import com.os.product_service.dto.response.product.UpdateProductResponse;
import com.os.product_service.model.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceProducer {

    private final KafkaTemplate<String, CreateProductResponse> kafkaTemplate;
    private final KafkaTemplate<String, DeleteProductRequest> kafkaDeleteTemplate;
    private final KafkaTemplate<String, UpdateProductResponse> kafkaUpdateTemplate;

    public SearchServiceProducer(KafkaTemplate<String, CreateProductResponse> kafkaTemplate, KafkaTemplate<String, DeleteProductRequest> kafkaDeleteTemplate, KafkaTemplate<String, UpdateProductResponse> kafkaUpdateTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaDeleteTemplate = kafkaDeleteTemplate;
        this.kafkaUpdateTemplate = kafkaUpdateTemplate;
    }

    @Value("${spring.kafka.template.product-topic}")
    private String defaultTopic;

    @Value("${spring.kafka.template.product-delete-topic}")
    private String deleteTopic;

    @Value("${spring.kafka.template.product-update-topic}")
    private String updateTopic;

    public void sendMessage(CreateProductResponse message) {
        System.out.println("Sending message: " + message);
        kafkaTemplate.send(defaultTopic, message);

    }
    public void deleteSendMessage(DeleteProductRequest message)
    {
        System.out.println("Deleting message: " + message);
        kafkaDeleteTemplate.send(deleteTopic,message);
    }
    public void updateSendMessage(UpdateProductResponse message)
    {
        System.out.println("Update message: " + message);
        kafkaUpdateTemplate.send(updateTopic,message);
    }

}
//spring.kafka.template.product-delete-topic