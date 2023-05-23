package com.example.MonitoringInternetShop.Services;

import com.example.MonitoringInternetShop.Models.OrderItem;
import com.example.MonitoringInternetShop.Models.Product;
import com.example.MonitoringInternetShop.Repositories.OrderItemRepository;
import com.example.MonitoringInternetShop.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<Product> getFilteredAndSortedProducts(String category, String sortBy) {
        List<Product> products;
        if (category != null && !category.isEmpty()) {
            products = productRepository.findByCategory(category);
        } else {
            products = productRepository.findAll();
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            if ("price".equalsIgnoreCase(sortBy)) {
                products.sort(Comparator.comparing(Product::getPrice));
            } else if ("popularity".equalsIgnoreCase(sortBy)) {
                products.sort(Comparator.comparing(Product::getSales).reversed());
            }
        }
        return products;
    }


    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getTopProducts() {
        return productRepository.findAll().stream()
                .sorted((p1, p2) -> p2.getSales() - p1.getSales())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Product findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(null);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void incrementProductStock(Long id, Integer incrementAmount) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStock(product.getStock() + incrementAmount);
            productRepository.save(product);
        } else {
            throw new RuntimeException("Продукт не найден с id: " + id);
        }
    }

    public void decrementProductStock(Long id, Integer decrementAmount) throws RuntimeException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getStock() >= decrementAmount) {
                product.setStock(product.getStock() - decrementAmount);
            } else {
                throw new RuntimeException("Товара на складе недостаточно с id: " + id);
            }
            productRepository.save(product);
        } else {
            throw new RuntimeException("Продукт не найден с id: " + id);
        }
    }

    public void updateProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findById(product.getId());
        if (existingProduct.isPresent()) {
            productRepository.save(product);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<OrderItem> findOrderItemsByProduct(Long productId) {
        return orderItemRepository.findAllByProduct_Id(productId);
    }

}
