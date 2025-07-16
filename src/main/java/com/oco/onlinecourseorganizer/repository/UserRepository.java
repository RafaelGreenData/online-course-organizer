package com.oco.onlinecourseorganizer.repository;

import com.oco.onlinecourseorganizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This interface allows automatic CRUD operations on the 'users' table
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query method — Spring will generate the SQL!
    User findByEmail(String email);
}

