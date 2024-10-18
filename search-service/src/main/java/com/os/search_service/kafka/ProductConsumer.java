package com.os.search_service.kafka;

import com.os.search_service.document.Product;
import com.os.search_service.dto.DeleteProductRequest;
import com.os.search_service.service.ProductElasticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductConsumer {

    private final ProductElasticSearchService productElasticSearchService;

    // ekleme
    @KafkaListener(topics = "${spring.kafka.template.product-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void createConsumer(ConsumerRecord<String, Product> payload) {
        productElasticSearchService.saveProduct(payload.value());
        System.out.println("Consumer tarafından oluştur mesaj alındı  : " + payload.value());
    }
    // silme
    @KafkaListener(topics = "${spring.kafka.template.product-delete-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerDeleteProductContainerFactory")
    public void deleteProductConsumer(ConsumerRecord<String, DeleteProductRequest> payload) {
        DeleteProductRequest product = payload.value();
        productElasticSearchService.deleteProduct(product.getId().toString());
        System.out.println("Consumer tarafından sil mesaj alındı  : " + payload.value());
    }

    //güncelleme
    @KafkaListener(topics = "${spring.kafka.template.product-update-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerUpdateProductContainerFactory")
    public void updateProductConsumer(ConsumerRecord<String, Product> payload) {
        productElasticSearchService.updateProduct(payload.value());
        System.out.println("Consumer tarafından güncelle mesaj alındı  : " + payload.value());
    }


}
