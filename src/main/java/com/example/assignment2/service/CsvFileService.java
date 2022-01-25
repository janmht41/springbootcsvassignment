package com.example.assignment2.service;

import com.example.assignment2.models.AllProducts;
import com.example.assignment2.models.ExpensiveProducts;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface CsvFileService {
    void save(MultipartFile multipartFile);
    List<AllProducts> getAllProducts();
    void saveExpensiveProducts(int price);
    List<ExpensiveProducts> getAllExpensiveProducts(int price);
}
