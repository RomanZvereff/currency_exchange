package com.currency_exchange.rest;

import com.currency_exchange.dao.daoImpl.ExchangeRateDaoImpl;
import com.currency_exchange.entity.ExchangeRate;

import javax.ws.rs.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Path("/exchange-rate")
public class ExchangeRateResource {

    private static final String CURRENCY_URL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

    @GET
    @Produces("application/json")
    @Path("/get-all-exchange-rates")
    public List<ExchangeRate> getAllExchangeRates() {
        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        return exchangeRateDao.getAll();
    }

    @DELETE
    @Path("/delete-exchange-rate/{id}")
    public void deleteExchangeRate(@PathParam("id") String id) {
        ExchangeRate exchangeRate = null;
        long exchangeRateId = Long.parseLong(id);
        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateDao.get(exchangeRateId);
        if(optionalExchangeRate.isPresent()) {
            exchangeRate = optionalExchangeRate.get();
            exchangeRateDao.delete(exchangeRate);
        }
    }

    @GET
    @Produces("application/json")
    @Path("new-exchange-rate")
    public String getAllExchangeRates1() {
        StringBuffer response = null;
        try {
            URL urlForGetRequest = new URL(CURRENCY_URL);
            String readLine;
            HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = new StringBuffer();
                while((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
            }else {
                return null;
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

}