package com.izicash.system.model.repository;

import com.izicash.system.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByLogin(String login);
}