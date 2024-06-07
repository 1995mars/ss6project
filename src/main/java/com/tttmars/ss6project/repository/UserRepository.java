package com.tttmars.ss6project.repository;

import com.tttmars.ss6project.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    public Users findByUsername(String username);
    Users findFirstById(Long id);
}