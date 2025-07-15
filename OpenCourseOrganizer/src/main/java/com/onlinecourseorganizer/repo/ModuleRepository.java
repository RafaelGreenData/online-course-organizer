package com.onlinecourseorganizer.repo;

import com.onlinecourseorganizer.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}
