package com.example.MonitoringInternetShop.Services;

import com.example.MonitoringInternetShop.Models.Product;
import com.example.MonitoringInternetShop.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getFilteredAndSortedProducts(String category, String sortBy) {
        List<Product> products = productRepository.findAll();

        if (category != null) {
            products = products.stream().filter(p -> p.getCategory().equals(category)).collect(Collectors.toList());
        }

        Comparator<Product> comparator = null;
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc" -> comparator = Comparator.comparing(Product::getPrice);
                case "priceDesc" -> comparator = (p1, p2) -> p2.getPrice().compareTo(p1.getPrice());
                case "popularity" -> comparator = (p1, p2) -> p2.getSales() - p1.getSales();
            }
        }

        if (comparator != null) {
            products = products.stream().sorted(comparator).collect(Collectors.toList());
        }

        return products;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
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

    public List<Product> findProductsByIds(List<Long> ids) {
        Iterable<Product> products = productRepository.findAllById(ids);
        return StreamSupport
                .stream(products.spliterator(), false)
                .collect(Collectors.toList());
    }
}
