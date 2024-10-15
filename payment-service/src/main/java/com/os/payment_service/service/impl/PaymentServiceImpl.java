package com.os.payment_service.service.impl;

import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import com.os.payment_service.client.CustomerClient;
import com.os.payment_service.client.OrderClient;
import com.os.payment_service.model.*;
import com.os.payment_service.model.OrderItem;
import com.os.payment_service.service.PaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderClient orderClient;
    private final CustomerClient customerClient;

    public PaymentServiceImpl(OrderClient orderClient, CustomerClient customerClient) {
        this.orderClient = orderClient;
        this.customerClient = customerClient;
    }

    @Override
    public String makePayment(String orderId,Long customerId,PaymentRequest paymentRequest) {
        Order order = orderClient.getByIdOrder(orderId);

        Customer customer = customerClient.getByIdUser(customerId);
        List<CustomerContactInfo> contactInfos = customerClient.getContactInfosByCustomerId(customer.getId());

        CustomerContactInfo contactInfo = contactInfos.get(0);

        Buyer buyer = new Buyer();
        buyer.setId(customer.getId().toString());
        buyer.setName(customer.getFirstName());
        buyer.setSurname(customer.getLastName());
        buyer.setGsmNumber(contactInfo.getGsmNumber());
        buyer.setEmail("random@example.com");
        buyer.setIdentityNumber(contactInfo.getIdentityNumber());
        buyer.setRegistrationAddress(contactInfo.getAddress());
        buyer.setIp("192.168.1.1");
        buyer.setCity(contactInfo.getCity());
        buyer.setCountry(contactInfo.getCountry());

        Options options = new Options();
        options.setApiKey("api-key");
        options.setSecretKey("secret-key");
        options.setBaseUrl("https://sandbox-api.iyzipay.com");

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId(orderId); // Order ID

        // Sepet öğelerinin toplam fiyatını hesapla
        BigDecimal totalBasketPrice = BigDecimal.ZERO;
        List<BasketItem> basketItems = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            BasketItem basketItem = new BasketItem();
            basketItem.setId("BI" + item.getProduct().getId()); // Unique ID
            basketItem.setName(item.getProduct().getName());
            basketItem.setCategory1("Random Category 1"); // Örnek kategori
            basketItem.setCategory2("Random Category 2"); // Örnek kategori
            basketItem.setItemType(BasketItemType.PHYSICAL.name());
            basketItem.setPrice(item.getProduct().getPrice());

            // Sepet toplam fiyatını artır
            totalBasketPrice = totalBasketPrice.add(item.getProduct().getPrice());
            basketItems.add(basketItem);
        }

        // Sepet fiyatı ve ödenen fiyatı ayarla
        request.setPrice(totalBasketPrice);  // Sepet öğelerinin toplam fiyatı
        request.setPaidPrice(totalBasketPrice);  // Aynı şekilde ödenen fiyat
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);

        // Kart bilgileri kullanıcıdan alınan PaymentRequest üzerinden ayarlanıyor
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName(paymentRequest.getCardHolderName());
        paymentCard.setCardNumber(paymentRequest.getCardNumber());
        paymentCard.setExpireMonth(paymentRequest.getExpireMonth());
        paymentCard.setExpireYear(paymentRequest.getExpireYear());
        paymentCard.setCvc(paymentRequest.getCvc());
        request.setPaymentCard(paymentCard);

        // Buyer'ı ödeme isteğine ekle
        request.setBuyer(buyer);

        // ShippingAddress ve BillingAddress bilgileri
        Address shippingAddress = new Address();
        shippingAddress.setContactName(buyer.getName() + " " + buyer.getSurname());
        shippingAddress.setCity(buyer.getCity());
        shippingAddress.setCountry(buyer.getCountry());
        shippingAddress.setAddress(buyer.getRegistrationAddress());
        shippingAddress.setZipCode("12345");
        request.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setContactName(buyer.getName() + " " + buyer.getSurname());
        billingAddress.setCity(buyer.getCity());
        billingAddress.setCountry(buyer.getCountry());
        billingAddress.setAddress(buyer.getRegistrationAddress());
        billingAddress.setZipCode("12345");
        request.setBillingAddress(billingAddress);

        // BasketItems'ı ödeme isteğine ekle
        request.setBasketItems(basketItems);

        // İyzico ödeme işlemi
        Payment result = Payment.create(request, options);

        // Ödeme durumu
        if ("success".equals(result.getStatus())) {
            return "Payment successful for order ID: " + orderId;
        } else {
            return "Payment failed: " + result.getErrorMessage();
        }
    }

    @Override
    public void order(String orderId) {
        Order order = orderClient.getByIdOrder(orderId);
        // Sipariş bilgilerini yazdır
        System.out.println("Order ID: " + order.getId());
       // System.out.println("Customer: " + order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
        System.out.println("Total Price: " + order.getTotalPrice());
        //System.out.println("Status: " + order.getStatus());

        // Sipariş öğelerini yazdır
        System.out.println("Order Items:");
        for (OrderItem item : order.getItems()) {
            System.out.println(" - Product ID: " + item.getProduct().getId());
            System.out.println("   Product Name: " + item.getProduct().getName());
            System.out.println("   Description: " + item.getProduct().getDescription());
            System.out.println("   Price: " + item.getProduct().getPrice());
            System.out.println("   Quantity: " + item.getQuantity());
            System.out.println("   Total Price: " + item.getTotalPrice());
        }
    }
}
