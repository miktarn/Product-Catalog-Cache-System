package com.people.pulse.tech.task.repository;

import com.people.pulse.tech.task.model.Product;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getProductsByCategory(String category);

}