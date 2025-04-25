package com.eCommerce.EcommerceBackend.repositories;

import com.eCommerce.EcommerceBackend.model.Category;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
