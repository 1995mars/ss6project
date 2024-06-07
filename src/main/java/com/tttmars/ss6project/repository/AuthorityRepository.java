package com.tttmars.ss6project.repository;

import com.tttmars.ss6project.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}