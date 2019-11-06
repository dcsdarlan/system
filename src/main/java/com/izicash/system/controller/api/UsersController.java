package com.izicash.system.controller.api;

import com.google.common.base.Strings;
import com.izicash.system.model.entity.UserEntity;
import com.izicash.system.model.service.UserService;
import com.izicash.system.request.UserCreateInput;
import com.izicash.system.request.UserUpdateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
//@Api(value = "users")
public class UsersController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity list(Model model) {
        Pageable pageable = PageRequest.of(1, 150);
        List<UserEntity> page = userService.findAll();
        if(page.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(page);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @RequestMapping(value = "/{limit}/{offset}", method = RequestMethod.GET)
    public ResponseEntity getPaginate(Model model, @PathVariable("limit") String limit, @PathVariable("offset") String offset) {
        int l = Integer.parseInt(limit);
        int o = Integer.parseInt(offset);
        PageRequest pageRequest = PageRequest.of(o, l, Sort.Direction.DESC, "id");
        Page<UserEntity> page = userService.findAll(pageRequest);
        if(page.getTotalElements() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(page);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(Model model, @Valid @RequestBody UserCreateInput input) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(input.getName().trim());
        userEntity.setEmail(input.getEmail().trim());
        userEntity.setStatusActive(input.isActive() ? 1 : 0);
        userEntity.setPassword(input.getPassword());
        userEntity = userService.save(userEntity);
        if(userEntity.getId() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userEntity);
    }


    @RequestMapping(value = "/{idUser}", method = RequestMethod.GET)
    public ResponseEntity get(Model model, @PathVariable("idUser") String idUser) {
        int id = Integer.parseInt(idUser);
        UserEntity userEntity = userService.findById(id).get();
        if(Objects.isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userEntity);
    }

    @RequestMapping(value = "/{idUser}", method = RequestMethod.PUT)
    public ResponseEntity update(Model model, @PathVariable("idUser") String idUser, @Valid @RequestBody UserUpdateInput input) {
        int id = Integer.parseInt(idUser);
        UserEntity userEntity = userService.findById(id).get();
        if(Objects.isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        userEntity.setName(input.getName().trim());
        userEntity.setEmail(input.getEmail().trim());
        userEntity.setStatusActive(input.isActive() ? 1 : 0);
        if(!Strings.isNullOrEmpty(input.getPassword())) userEntity.setPassword(input.getPassword());
        userEntity = userService.save(userEntity);
        if(Objects.isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userEntity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(Model model, @PathVariable("id") String idUser) {
        int id = Integer.parseInt(idUser);
        UserEntity userEntity = userService.findById(id).get();
        if(Objects.isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        userService.delete(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
