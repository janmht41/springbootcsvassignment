package com.example.assignment2.controller;

import com.example.assignment2.message.ResponseMessage;
import com.example.assignment2.models.AllProducts;
import com.example.assignment2.models.ExpensiveProducts;
import com.example.assignment2.service.CsvFileService;
import com.example.assignment2.utils.CsvUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/csv")
public class CsvFileController {
    @Autowired
    CsvFileService csvFileService;

    private static final Logger logger = LogManager.getLogger(CsvFileController.class);

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CsvUtil.hasCSVFormat(file)) {
            try {
                csvFileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                logger.info("file upload successful");
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/products")
    public ResponseEntity<List<AllProducts>> getAllProducts() {
        try {
            List<AllProducts> products = csvFileService.getAllProducts();

            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/expensive")
    public ResponseEntity<List<ExpensiveProducts>> getExpensiveProductsOnly(@RequestParam(name = "priceFilter") int price) {
        try {
            List<ExpensiveProducts> products = csvFileService.getAllExpensiveProducts(price);

            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
