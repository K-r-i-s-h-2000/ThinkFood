/**
 * Repository interface for performing CRUD operations on the Category entity.
 * This interface provides methods to interact with the underlying database table.
 *
 * @author agrah.mv
 * @since 26 October 2023
 * @version 2.0
 *
 */
package com.thinkpalm.thinkfood.repository;


import com.thinkpalm.thinkfood.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByIsActiveTrue(Pageable pageable);

//    Optional<Category> findByCategoryNameAndIsActiveTrue(String categoryName);

    List<Category> findByIsActive(boolean isActive);
}
