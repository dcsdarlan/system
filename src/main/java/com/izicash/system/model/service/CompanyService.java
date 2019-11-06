package com.izicash.system.model.service;

import com.google.gson.Gson;
import com.izicash.system.model.entity.CompanyEntity;
import com.izicash.system.model.entity.UserEntity;
import com.izicash.system.model.repository.CompanyRepository;
import com.izicash.system.model.repository.UserRepository;
import com.izicash.system.util.log.LogService;
import com.izicash.system.util.security.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyService extends DBService {

    @Autowired
    public CompanyRepository companyRepository;

    public Optional<CompanyEntity> findById(int id) {
        return companyRepository.findById(id);
    }

    public List<CompanyEntity> findAll() {
        return companyRepository.findAll();
    }

    public Page<CompanyEntity> findAll(PageRequest pageRequest) {
        return companyRepository.findAll(pageRequest);
    }

    public CompanyEntity save(CompanyEntity companyEntity) {
        return companyRepository.save(companyEntity);
    }

    public void delete(CompanyEntity companyEntity) {
        companyRepository.delete(companyEntity);
    }
}