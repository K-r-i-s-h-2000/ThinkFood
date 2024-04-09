/**
 * Repository interface for performing CRUD operations on the ReviewsAndRatings entity.
 * This interface provides methods to interact with the underlying database table.
 *
 * @author agrah.mv
 * @since 30 October 2023
 * @version 2.0
 *
 */
package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.ReviewsAndRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsAndRatingsRepository extends JpaRepository<ReviewsAndRatings, Long> {

}
