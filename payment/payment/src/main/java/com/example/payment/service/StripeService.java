package com.example.payment.service;

import com.example.payment.events.PaymentEvent;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

@Service
public class StripeService {
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    private final Cache<Long, String> sessionCache = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();


    public String createSession(PaymentEvent event) throws StripeException
    {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()    //begin the construction
                .setMode(SessionCreateParams.Mode.PAYMENT)            //Specifies that the session is for a one-time payment.
                .setSuccessUrl(successUrl)                            //Sets the URL to redirect to upon successful payment.
                .setCancelUrl(cancelUrl)                              //Sets the URL to redirect to if the payment is canceled.
                .setClientReferenceId(String.valueOf(event.getOrderId()))  //set the refernce id
                .putMetadata("clientId", String.valueOf(event.getClientId()))   //set the meta data
                .putMetadata("orderId", String.valueOf(event.getOrderId()))
                .addLineItem(                                         //Adds an item to the session's line items.
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)  // Set the quantity here//Sets the quantity of the item.
                                .setPriceData(                        //Defines the pricing information for the item.
        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("inr")   //Sets the currency to USD.
                .setUnitAmount((long) (event.getPrice() * 100))       //Sets the unit amount to 1000 cents
                                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName("Demo Product")  //Sets the product's name
                                                        .build())                  // Finalizes the construction of the session parameters.


                .build())
                                .build()).build();

        Session session = Session.create(params);

        sessionCache.put(event.getOrderId(), session.getUrl());
        return session.getUrl();

    }
    public String getSessionUrl(Long orderId) {
        return sessionCache.getIfPresent(orderId);
    }
}
