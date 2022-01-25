package com.example.assignment2.repositories;

import com.example.assignment2.models.AllProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllProductsRepository extends JpaRepository<AllProducts, Long>{
}
