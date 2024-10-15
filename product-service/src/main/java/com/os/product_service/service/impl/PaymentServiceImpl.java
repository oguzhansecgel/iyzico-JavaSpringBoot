package com.os.product_service.service.impl;

import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import com.os.product_service.model.Customer;
import com.os.product_service.model.PaymentRequest;
import com.os.product_service.model.Product;
import com.os.product_service.repository.CustomerRepository;
import com.os.product_service.repository.ProductRepository;
import com.os.product_service.service.PaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public PaymentServiceImpl(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }
    @Override
    public String makePayment(Long productId, Long customerId, PaymentRequest payment) {
        Product product = productRepository.findById(productId).orElse(null);
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (product == null) {
            return "Product not found";
        }

        if (customer == null) {
            return "Customer not found";
        }

        // Ürünün fiyatını al
        BigDecimal productPrice = product.getPrice();

        // İyzico API ayarları
        Options options = new Options();
        options.setApiKey("apikey");
        options.setSecretKey("secretkey");
        options.setBaseUrl("https://sandbox-api.iyzipay.com");

        // Ödeme isteği oluştur
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId("123456789");
        request.setPrice(productPrice);  // Ürünün fiyatını ayarla
        request.setPaidPrice(productPrice);  // Aynı şekilde ödenen fiyat
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);

        // Kart bilgileri
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName(payment.getCardHolderName());
        paymentCard.setCardNumber(payment.getCardNumber());
        paymentCard.setExpireMonth(payment.getExpireMonth());
        paymentCard.setExpireYear(payment.getExpireYear());
        paymentCard.setCvc(payment.getCvc());
        request.setPaymentCard(paymentCard);

        // Buyer bilgileri customer'dan dolduruluyor
        Buyer buyer = new Buyer();
        buyer.setId(String.valueOf(customer.getId()));
        buyer.setName(customer.getName());
        buyer.setSurname(customer.getSurname());
        buyer.setGsmNumber(customer.getGsmNumber());
        buyer.setEmail(customer.getEmail());
        buyer.setIdentityNumber(customer.getIdentityNumber());
        buyer.setRegistrationAddress(customer.getAddress());
        buyer.setIp("85.34.78.112"); // İsteği yapan kullanıcının IP adresi
        buyer.setCity(customer.getCity());
        buyer.setCountry(customer.getCountry());

        // Buyer'ı ödeme isteğine ekle
        request.setBuyer(buyer);

        // ShippingAddress ve BillingAddress bilgileri
        Address shippingAddress = new Address();
        shippingAddress.setContactName(buyer.getName() + " " + buyer.getSurname());
        shippingAddress.setCity(customer.getCity());
        shippingAddress.setCountry(customer.getCountry());
        shippingAddress.setAddress(customer.getAddress());
        shippingAddress.setZipCode("12345");
        request.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setContactName(buyer.getName() + " " + buyer.getSurname());
        billingAddress.setCity(customer.getCity());
        billingAddress.setCountry(customer.getCountry());
        billingAddress.setAddress(customer.getAddress());
        billingAddress.setZipCode("12345");
        request.setBillingAddress(billingAddress);

        // BasketItem'ları oluştur
        List<BasketItem> basketItems = new ArrayList<>();
        BasketItem firstBasketItem = new BasketItem();
        firstBasketItem.setId("BI101"); // Unique ID
        firstBasketItem.setName(product.getName());
        firstBasketItem.setCategory1("Collectibles");
        firstBasketItem.setCategory2("Accessories");
        firstBasketItem.setItemType(BasketItemType.PHYSICAL.name());
        firstBasketItem.setPrice(productPrice);
        basketItems.add(firstBasketItem);

        // BasketItems'ı ödeme isteğine ekle
        request.setBasketItems(basketItems);

        // İyzico ödeme işlemi
        Payment result = Payment.create(request, options);

        // Ödeme durumu
        if ("success".equals(result.getStatus())) {
            return "Payment successful for product: " + product.getName();
        } else {
            return "Payment failed: " + result.getErrorMessage();
        }
    }

}
