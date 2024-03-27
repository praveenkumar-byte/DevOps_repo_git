package com.nagarro.javaMiniAssignment2.repository;

import com.nagarro.javaMiniAssignment2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User ,Long> {
}
