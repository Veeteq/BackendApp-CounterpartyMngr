package com.veeteq.finance.counterparty.search;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.veeteq.finance.counterparty.dto.BankDataDTO;

public class SearchCriteria {

    private String key;
    private Object value;

    public SearchCriteria(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public static Map<String, String> buildSearchCriteria(BankDataDTO data) {
        Map<String, String> searchCriteria = new HashMap<>();

        for (Field field : data.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object obj = field.get(data);
                if (obj != null && field.getType().equals(String.class)) {
                    String value = (String) obj;
                    searchCriteria.put(field.getName(), value);
                }
            } catch (IllegalAccessException exc) {

            }
        }

        return searchCriteria;
    }

}
