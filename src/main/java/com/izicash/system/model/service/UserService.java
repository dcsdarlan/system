package com.izicash.system.model.service;

import com.google.gson.Gson;
import com.izicash.system.model.entity.UserEntity;
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
public class UserService extends DBService {

    @Autowired
    public UserRepository userRepository;

    public JWTToken logon(String login, String password){
        UserEntity userEntity = validate(login, password);
        if(Objects.nonNull(userEntity)) {
            LogService.info("Usuario encontrado: " + new Gson().toJson(userEntity));
            JWTToken token = new JWTToken();
            token.user = userEntity;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String timeNow = dateFormat.format(new Date());
            token.date = timeNow;
            return token;
        }
        return null;
    }

    public UserEntity validate(String login, String password){
        UserEntity user = userRepository.findByLogin(login);
        if(Objects.nonNull(user) && user.getPassword().equals(password) && user.getStatusActive() == 1){
            return user;
        }
        return null;
    }

    public Optional<UserEntity> findById(int id) {
        return userRepository.findById(id);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Page<UserEntity> findAll(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void delete(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }
}