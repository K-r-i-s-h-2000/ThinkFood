/**
 * The repository provides methods to perform database operations related to email templates.
 * author: Sharon Sam
 * since : 01 November 2023
 * version : 2.0
 */


package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.EmailTemplate;
import com.thinkpalm.thinkfood.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    /**
     * Retrieves an {@link Optional} of {@link EmailTemplate} from the database based on the specified id.
     *
     * @param id The unique identifier of the email template.
     * @return An {@link Optional} containing the found {@link EmailTemplate} or an empty {@link Optional} if not found.
     */
    Optional<EmailTemplate> findById(Long id);
}

