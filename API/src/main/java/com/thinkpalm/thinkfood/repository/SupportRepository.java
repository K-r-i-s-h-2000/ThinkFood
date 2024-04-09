/**
 * Repository interface for performing CRUD operations on the Support entity.
 * This interface provides methods to interact with the underlying database table.
 *
 * @author agrah.mv
 * @since 31 October 2023
 * @version 2.0
 *
 */
package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {
}
