package com.fares.MoxieBell.repository;

import com.fares.MoxieBell.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // We will definitely need this later for Login / Spring Security
    Optional<User> findByEmail(String email);

}