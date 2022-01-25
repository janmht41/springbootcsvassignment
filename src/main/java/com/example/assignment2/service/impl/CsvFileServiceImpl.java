package com.example.assignment2.service.impl;

import com.example.assignment2.models.AllProducts;
import com.example.assignment2.models.ExpensiveProducts;
import com.example.assignment2.repositories.AllProductsRepository;
import com.example.assignment2.repositories.ExpensiveProductRepository;
import com.example.assignment2.service.CsvFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.assignment2.utils.CsvUtil.csvToList;

@Service
public class CsvFileServiceImpl implements CsvFileService {
    @Autowired
    AllProductsRepository allProductsRepository;

    @Autowired
    ExpensiveProductRepository expensiveProductRepository;

    @Override
    public void save(MultipartFile multipartFile) {
        try {
            List<AllProducts> products = csvToList(multipartFile.getInputStream());
            allProductsRepository.saveAll(products);
        } catch (IOException e) {
            throw new RuntimeException("failed to store csv data: " + e.getMessage());
        }
    }

    @Override
    public List<AllProducts> getAllProducts() {
        return allProductsRepository.findAll();
    }

    @Override
    public void saveExpensiveProducts(int price) {
        List<AllProducts> products = getAllProducts();

        if(products.isEmpty())
            return;

            List<AllProducts> productsList = products
                    .stream()
                    .filter(product -> product.getProductPrice() >= price)
                    .collect(Collectors.toList());

            List<ExpensiveProducts> expensiveProducts = new ArrayList<>();

            for (AllProducts product : productsList) {
                expensiveProducts.add(
                        ExpensiveProducts.builder()
                                .name(product.getProductName())
                                .description(product.getProductDescription())
                                .price(product.getProductPrice())
                                .brand(product.getBrandName())
                                .build());
            }

        expensiveProductRepository.saveAll(expensiveProducts);

    }

    @Override
    public List<ExpensiveProducts> getAllExpensiveProducts(int price) {
        saveExpensiveProducts(price);
        return expensiveProductRepository.findAll();
    }
}
