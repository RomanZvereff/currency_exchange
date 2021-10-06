package com.currency_exchange.entity;

import java.io.Serializable;
import java.util.Calendar;

public class ExchangeRate implements Serializable, Comparable<ExchangeRate> {

    private int exchangeRateId;
    private Calendar exchangeRateDate;
    private String exchangeRateCcy;
    private String exchangeRateBaseCcy;
    private float exchangeRateBuy;
    private float exchangeRateSale;

    public ExchangeRate() {
    }

    public int getExchangeRateId() {
        return exchangeRateId;
    }

    public void setExchangeRateId(int exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
    }

    public Calendar getExchangeRateDate() {
        return exchangeRateDate;
    }

    public void setExchangeRateDate(Calendar exchangeRateDate) {
        this.exchangeRateDate = exchangeRateDate;
    }

    public String getExchangeRateCcy() {
        return exchangeRateCcy;
    }

    public void setExchangeRateCcy(String exchangeRateCcy) {
        this.exchangeRateCcy = exchangeRateCcy;
    }

    public String getExchangeRateBaseCcy() {
        return exchangeRateBaseCcy;
    }

    public void setExchangeRateBaseCcy(String exchangeRateBaseCcy) {
        this.exchangeRateBaseCcy = exchangeRateBaseCcy;
    }

    public float getExchangeRateBuy() {
        return exchangeRateBuy;
    }

    public void setExchangeRateBuy(float exchangeRateBuy) {
        this.exchangeRateBuy = exchangeRateBuy;
    }

    public float getExchangeRateSale() {
        return exchangeRateSale;
    }

    public void setExchangeRateSale(float exchangeRateSale) {
        this.exchangeRateSale = exchangeRateSale;
    }

    @Override
    public int compareTo(ExchangeRate o) {
        return exchangeRateDate.compareTo(o.getExchangeRateDate());
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "exchangeRateId=" + exchangeRateId +
                ", exchangeRateDate=" + exchangeRateDate.getTime() +
                ", exchangeRateCcy='" + exchangeRateCcy + '\'' +
                ", exchangeRateBaseCcy='" + exchangeRateBaseCcy + '\'' +
                ", exchangeRateBuy=" + exchangeRateBuy +
                ", exchangeRateSale=" + exchangeRateSale +
                '}';
    }
}
