package com.example.assignment2.models;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="allProducts")
@Builder
public class AllProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long productId;

    @Column(name="name")
    private String productName;

    @Column(name="description")
    private String productDescription;

    @Column(name="price")
    private int productPrice;

    @Column(name="brand")
    private String brandName;
}
