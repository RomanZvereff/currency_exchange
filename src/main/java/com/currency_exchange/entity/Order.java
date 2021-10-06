package com.currency_exchange.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

public class Order implements Serializable {

    private long orderId;
    private String orderNumber;
    private User user;
    private Calendar orderDate;
    private Calendar rateDate;
    private String currency;
    private String baseCurrency;
    private String orderOperation;
    private float exchangeRate;
    private float currencyAmount;
    private float baseCurrencyAmount;
    private String orderStatus;

    public Order() {
    }

    public Order(Calendar key, double sumCurrAmt, double sumBaseCurrAmt) {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Calendar orderDate) {
        this.orderDate = orderDate;
    }

    public Calendar getRateDate() {
        return rateDate;
    }

    public void setRateDate(Calendar rateDate) {
        this.rateDate = rateDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getOrderOperation() {
        return orderOperation;
    }

    public void setOrderOperation(String orderOperation) {
        this.orderOperation = orderOperation;
    }

    public float getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(float exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public float getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(float currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public float getBaseCurrencyAmount() {
        return baseCurrencyAmount;
    }

    public void setBaseCurrencyAmount(float baseCurrencyAmount) {
        this.baseCurrencyAmount = baseCurrencyAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public static String generateOrderNumber() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 20) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

}
