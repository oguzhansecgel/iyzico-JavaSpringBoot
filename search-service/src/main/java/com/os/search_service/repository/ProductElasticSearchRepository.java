package com.os.search_service.repository;

import com.os.search_service.document.Product;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductElasticSearchRepository extends ElasticsearchRepository<Product,String> {

    @Query("{\"wildcard\": {\"name\": \"*?0*\"}}")
    List<Product> searchByProductName(String productName);

    List<Product> findByPriceBetween(BigDecimal lower, BigDecimal upper);

}
