package com.os.payment_service.service.impl;

import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import com.os.payment_service.client.BasketClient;
import com.os.payment_service.client.CustomerClient;
import com.os.payment_service.client.OrderClient;
import com.os.payment_service.exception.type.ContactInfosNotFoundException;
import com.os.payment_service.kafka.NotificationProducer;
import com.os.payment_service.model.*;
import com.os.payment_service.model.OrderItem;
import com.os.payment_service.service.PaymentService;
import com.os.payment_service.utils.message.ContactInfosMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderClient orderClient;
    private final CustomerClient customerClient;
    private final BasketClient basketClient;
    private final NotificationProducer notificationProducer;

    public PaymentServiceImpl(OrderClient orderClient, CustomerClient customerClient, BasketClient basketClient, NotificationProducer notificationProducer) {
        this.orderClient = orderClient;
        this.customerClient = customerClient;
        this.basketClient = basketClient;
        this.notificationProducer = notificationProducer;
    }

    @Override
    public String makePayment(String orderId,PaymentRequest paymentRequest) {
        Notification notification = new Notification();
        Order order = orderClient.getByIdOrder(orderId);
        Customer customer = customerClient.getByIdUser(order.getCustomer().getId());
        notification.setEmail(customer.getEmail());
        notification.setFirstName(customer.getFirstName());
        notification.setLastName(customer.getLastName());
        notification.setTotalPrice(order.getTotalPrice());
        List<CustomerContactInfo> contactInfos = customerClient.getContactInfosByCustomerId(customer.getId());

        if (contactInfos.size() == 0) {
            throw new ContactInfosNotFoundException(ContactInfosMessage.CONTACT_INFOS_NOT_FOUND);
        }
        CustomerContactInfo contactInfo = contactInfos.get(0);

        Buyer buyer = new Buyer();
        buyer.setId(customer.getId().toString());
        buyer.setName(customer.getFirstName());
        buyer.setSurname(customer.getLastName());
        buyer.setGsmNumber(contactInfo.getGsmNumber());
        buyer.setEmail(customer.getEmail());
        buyer.setIdentityNumber(contactInfo.getIdentityNumber());
        buyer.setRegistrationAddress(contactInfo.getAddress());
        buyer.setIp("192.168.1.1"); // İsteği yapan kullanıcının IP adresi
        buyer.setCity(contactInfo.getCity());
        buyer.setCountry(contactInfo.getCountry());

        Options options = new Options();
        options.setApiKey("apikey");
        options.setSecretKey("secretkey");
        options.setBaseUrl("https://sandbox-api.iyzipay.com");

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId(orderId);

        BigDecimal totalBasketPrice = BigDecimal.ZERO;
        List<BasketItem> basketItems = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            BasketItem basketItem = new BasketItem();
            basketItem.setId("BI" + item.getProduct().getId());
            basketItem.setName(item.getProduct().getName());
            basketItem.setCategory1(""+item.getProduct().getCategoryId());
            basketItem.setCategory2(""+item.getProduct().getCategoryId());
            basketItem.setItemType(BasketItemType.PHYSICAL.name());
            basketItem.setPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            totalBasketPrice = totalBasketPrice.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            basketItems.add(basketItem);


        }

        request.setPrice(totalBasketPrice);
        request.setPaidPrice(totalBasketPrice);
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);

        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName(paymentRequest.getCardHolderName());
        paymentCard.setCardNumber(paymentRequest.getCardNumber());
        paymentCard.setExpireMonth(paymentRequest.getExpireMonth());
        paymentCard.setExpireYear(paymentRequest.getExpireYear());
        paymentCard.setCvc(paymentRequest.getCvc());
        request.setPaymentCard(paymentCard);

        request.setBuyer(buyer);

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

        request.setBasketItems(basketItems);

        Payment result = Payment.create(request, options);
        List<ProductNotification> products = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            ProductNotification productNotification = new ProductNotification();
            productNotification.setId(item.getProduct().getId());
            productNotification.setName(item.getProduct().getName());
            productNotification.setDescription(item.getProduct().getDescription());
            productNotification.setPrice(item.getProduct().getPrice());
            productNotification.setQuantity(item.getQuantity());
            products.add(productNotification);
        }
        notification.setProducts(products);
        notificationProducer.sendMessage(notification);
        if ("success".equals(result.getStatus())) {
            basketClient.deleteBasketBeforeOrder(order.getBasketId());
            return "Payment successful for order ID: " + orderId;
        } else {
            return "Payment failed: " + result.getErrorMessage();
        }
    }


}
