package com.ichwan.springredis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * id dan amount otomatis akan menjadi Map key dan value
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String id;
    private Long amount;
}
