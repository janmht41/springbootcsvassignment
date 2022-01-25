package com.example.assignment2.utils;


import com.example.assignment2.models.AllProducts;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



public class CsvUtil {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static boolean hasCorrectHeaders(BufferedReader br) throws IOException {
        return br.readLine().equals("name,description,price,brand");
    }

    public static List<AllProducts> csvToList(InputStream is) throws IOException {
        List<AllProducts> allProductsList;

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            if (!hasCorrectHeaders(br))
                throw new Exception("File has invalid headers");

            allProductsList = new ArrayList<>();

            br.lines().skip(1).forEach(line -> {

                String[] items = line.split(",");
                try {
                    if (items.length != 4)
                        throw new Exception("File has incorrect number of items in rows");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                allProductsList.add(AllProducts.builder()
                        .productName(items[0])
                        .productDescription(items[1])
                        .productPrice(Integer.parseInt(items[2]))
                        .brandName(items[3])
                        .build()
                );
            });

        } catch (Exception e) {
            throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
        }

        return allProductsList;
    }



}
