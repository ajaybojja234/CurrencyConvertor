package com.example.CurrencyConverter.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertedResult {
    private String from;
    private String to;
    private float amount;
    private float result;
    private float rate;
}
