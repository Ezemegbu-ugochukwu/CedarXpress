package com.example.cedarxpressliveprojectjava010.repository;

import com.example.cedarxpressliveprojectjava010.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
