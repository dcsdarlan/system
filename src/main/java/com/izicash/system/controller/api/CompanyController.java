package com.izicash.system.controller.api;

import com.google.common.base.Strings;
import com.izicash.system.model.entity.CompanyEntity;
import com.izicash.system.model.entity.UserEntity;
import com.izicash.system.model.service.CompanyService;
import com.izicash.system.model.service.UserService;
import com.izicash.system.request.CompanyCreateInput;
import com.izicash.system.request.CompanyUpdateInput;
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
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity list(Model model) {
        Pageable pageable = PageRequest.of(1, 150);
        List<CompanyEntity> page = companyService.findAll();
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
        Page<CompanyEntity> page = companyService.findAll(pageRequest);
        if(page.getTotalElements() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(page);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(Model model, @Valid @RequestBody CompanyCreateInput input) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName(input.getName().trim());
        companyEntity = companyService.save(companyEntity);
        if(companyEntity.getId() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(companyEntity);
    }


    @RequestMapping(value = "/{idCompany}", method = RequestMethod.GET)
    public ResponseEntity get(Model model, @PathVariable("idCompany") String idCompany) {
        int id = Integer.parseInt(idCompany);
        CompanyEntity companyEntity = companyService.findById(id).get();
        if(Objects.isNull(companyEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(companyEntity);
    }

    @RequestMapping(value = "/{idCompany}", method = RequestMethod.PUT)
    public ResponseEntity update(Model model, @PathVariable("idCompany") String idCompany, @Valid @RequestBody CompanyUpdateInput input) {
        int id = Integer.parseInt(idCompany);
        CompanyEntity companyEntity = companyService.findById(id).get();
        if(Objects.isNull(companyEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        companyEntity.setName(input.getName().trim());
        companyEntity = companyService.save(companyEntity);
        if(Objects.isNull(companyEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(companyEntity);
    }

    @RequestMapping(value = "/{idCompany}", method = RequestMethod.DELETE)
    public ResponseEntity delete(Model model, @PathVariable("idCompany") String idCompany) {
        int id = Integer.parseInt(idCompany);
        CompanyEntity companyEntity = companyService.findById(id).get();
        if(Objects.isNull(companyEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        companyService.delete(companyEntity);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
