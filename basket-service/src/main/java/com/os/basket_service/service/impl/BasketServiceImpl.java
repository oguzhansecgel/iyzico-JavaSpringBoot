package com.os.basket_service.service.impl;

import com.os.basket_service.client.CustomerClient;
import com.os.basket_service.client.ProductClient;
import com.os.basket_service.model.Basket;
import com.os.basket_service.model.BasketItem;
import com.os.basket_service.model.CustomerDto;
import com.os.basket_service.model.ProductDto;
import com.os.basket_service.repository.BasketRepository;
import com.os.basket_service.service.BasketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final ProductClient productClient;
    private final CustomerClient customerClient;
    private static final Logger logger = LoggerFactory.getLogger(BasketService.class);

    public BasketServiceImpl(BasketRepository basketRepository, ProductClient productClient, CustomerClient customerClient) {
        this.basketRepository = basketRepository;
        this.productClient = productClient;
        this.customerClient = customerClient;
    }

    @Override
    @CacheEvict(value = "basket",key = "'getAll'")
    public Basket createBasketItem(Long customerId, Map<Long, Integer> productQuantities) {
        Optional<CustomerDto> optionalCustomer = customerClient.getByIdUser(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }
        CustomerDto customerDto = optionalCustomer.get();

        Basket basket = basketRepository.findByCustomerId(customerId);
        if (basket == null) {
            basket = new Basket();
            basket.setCustomer(customerDto);
        }

        List<BasketItem> items = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue() != null ? entry.getValue() : 1;

            Optional<ProductDto> optionalProduct = productClient.getProductById(productId);
            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Product not found");
            }
            ProductDto productDto = optionalProduct.get();

            BasketItem basketItem = new BasketItem();
            basketItem.setProduct(productDto);
            basketItem.setQuantity(quantity);
            basketItem.setTotalPrice(productDto.getPrice().multiply(BigDecimal.valueOf(quantity)));

            items.add(basketItem);
            totalPrice = totalPrice.add(basketItem.getTotalPrice());
        }

        basket.setItems(items);
        basket.setTotalPrice(totalPrice);

        return basketRepository.save(basket);
    }

    @Override
    public Basket productController(Long productId) {
        Optional<ProductDto> productDto =  productClient.getProductById(productId);
        ProductDto productDto1 =  productDto.get();
        System.out.println(
                "Product ID: " + productDto1.getId() +
                        ", Name: " + productDto1.getName() +
                        ", Description: " + productDto1.getDescription() +
                        ", Price: " + productDto1.getPrice() +
                        ", Category ID: " + productDto1.getCategoryId()
        );
        return null;
    }

    @Override
    @Cacheable(value = "basket",key = "'#customerId'")
    public Basket findByCustomersBasket(Long customerId) {
        Optional<CustomerDto> customerIdExisting = customerClient.getByIdUser(customerId);
        if (customerIdExisting.isEmpty())
            throw new RuntimeException("Customer Not Found");
        return basketRepository.findByCustomerId(customerId);
    }

    @Override
    @Cacheable(value = "basket",key = "#basketId")
    public Optional<Basket> findByBasketId(String basketId) {
        Optional<Basket> basket = basketRepository.findById(basketId);
        logger.info("metot çalıştı.");
        if (basket.isEmpty())
            throw new RuntimeException("basket Not Found");
        return basket;
    }

    @Override
    @CacheEvict(value = "basket",key = "#basketId",allEntries = true)
    public void deleteBasket(String basketId) {
        Optional<Basket> basket = basketRepository.findById(basketId);
        if (basket.isEmpty())
            throw new RuntimeException("basket Not Found");

        basketRepository.deleteById(basketId);
    }
}
