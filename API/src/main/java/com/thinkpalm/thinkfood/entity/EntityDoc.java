package com.thinkpalm.thinkfood.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Abstract class for all Entity class.
 * <p></p>
 *
 * @author sreemanikandan.k
 * @version 14/09/2023
 * @since 14/09/2023
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class EntityDoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", columnDefinition = "DATE")
    private LocalDate createdDate;

    @CreationTimestamp
    @Column(name = "created_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDateTime;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date", columnDefinition = "DATE")
    private LocalDate lastModifiedDate;

    @UpdateTimestamp
    @Column(name = "last_modified_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastModifiedDateTime;
}

