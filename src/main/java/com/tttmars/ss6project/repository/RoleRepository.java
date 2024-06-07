package com.tttmars.ss6project.repository;

import com.tttmars.ss6project.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {
}

