package ru.geekbrains;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("prototype")
public class Cart {

    private final Map<Long, Integer> productMap = new ConcurrentHashMap<>();

    public void addToCart(Long id, Integer addQuantity) {
        Integer quantity = productMap.get(id);
        if (quantity == null) {
            productMap.put(id, addQuantity);
        } else {
            productMap.replace(id, quantity + addQuantity);
        }
    }

    public void addToCart(Long id) {
        addToCart(id, 1);
    }

    public void RemoveFromCart(Long id) {
        productMap.remove(id);
    }

    public Integer getQuantity(Long id) {
        return productMap.get(id) == null ? 0 : productMap.get(id);
    }

    public void setQuantity(Long id, Integer quantity) {
        productMap.replace(id, quantity);
    }

    public List<Long> getProductList() {
        return new ArrayList<>(productMap.keySet());
    }
}
