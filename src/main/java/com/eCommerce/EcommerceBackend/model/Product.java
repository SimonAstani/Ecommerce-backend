package com.eCommerce.EcommerceBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productid;
    private String productName;
    private String description;
    private Integer quantity;
    private double price;
    private double socialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
