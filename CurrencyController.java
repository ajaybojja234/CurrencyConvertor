package com.example.CurrencyConverter.Controller;

import com.example.CurrencyConverter.Model.ConvertedResult;
import com.example.CurrencyConverter.Service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @QueryMapping
    public ConvertedResult convert(@Argument String from, @Argument String to, @Argument float amount) {
        return currencyService.convert(from, to, amount);
    }
}
