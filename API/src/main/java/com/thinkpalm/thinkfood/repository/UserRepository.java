package com.thinkpalm.thinkfood.repository;

import com.thinkpalm.thinkfood.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findByEmail(String email);
}
