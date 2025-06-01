package com.example.CurrencyConverter.Service;

import com.example.CurrencyConverter.Model.ConvertedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Service
public class CurrencyService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.url}")
    private String apiUrl;

    private final WebClient webClient = WebClient.create();

    @Cacheable(value = "currency", key = "#from + ':' + #to", unless = "#result == null")
    public float getRate(String from, String to) {
        String url = String.format("%s/convert?from=%s&to=%s&amount=1&apikey=%s", apiUrl, from, to, apiKey);
        Map response = webClient.get().uri(url).retrieve().bodyToMono(Map.class).block();

        Map rates = (Map) response.get("rates");
        return ((Number) rates.get(to)).floatValue();
    }

    public ConvertedResult convert(String from, String to, float amount) {
        float rate = getRate(from, to);
        float result = amount * rate;
        ConvertedResult conversion = new ConvertedResult(from, to, amount, result, rate);
        logConversion(conversion);
        return conversion;
    }

    private void logConversion(ConvertedResult result) {
        // Use Logger or structured log for Logstash
        Logger logger = LoggerFactory.getLogger(CurrencyService.class);
        logger.info("CurrencyConversion: {}", result);
    }
}
