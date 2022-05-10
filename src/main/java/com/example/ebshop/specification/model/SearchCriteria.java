package com.example.ebshop.specification.model;

import lombok.Data;

@Data
public class SearchCriteria {
    private String key;
    private String operator;
    private Object value;

    public SearchCriteria() {
    }

    public SearchCriteria(String key, String operator, Object value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }
}
