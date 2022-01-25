package com.example.assignment2.repositories;

import com.example.assignment2.models.ExpensiveProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensiveProductRepository extends JpaRepository<ExpensiveProducts, Long> {
}
