package com.example.MonitoringInternetShop.Services;

import com.example.MonitoringInternetShop.Models.Product;
import com.example.MonitoringInternetShop.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getTopProducts() {
        return productRepository.findAll().stream()
                .sorted((p1, p2) -> p2.getSales() - p1.getSales())
                .limit(10)
                .collect(Collectors.toList());
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
