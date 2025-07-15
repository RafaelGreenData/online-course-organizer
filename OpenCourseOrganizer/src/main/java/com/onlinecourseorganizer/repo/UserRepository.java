package com.onlinecourseorganizer.repo;

import com.onlinecourseorganizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); //login
}
