package com.izicash.system.model.repository;

import com.izicash.system.model.entity.CompanyEntity;
import com.izicash.system.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {
}