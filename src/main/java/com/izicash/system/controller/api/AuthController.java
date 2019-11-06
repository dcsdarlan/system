package com.izicash.system.controller.api;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.izicash.system.model.service.UserService;
import com.izicash.system.request.LoginInput;
import com.izicash.system.response.VersionOutput;
import com.izicash.system.util.log.LogService;
import com.izicash.system.util.security.JWTService;
import com.izicash.system.util.security.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Value("${app.name}")
    private String systemName;
    @Value("${app.version}")
    private String systemVersion;
    @Value("${app.build}")
    private String systemBuild;

    @RequestMapping(value = "/logon", method = RequestMethod.POST)
    public Callable<ResponseEntity> logon(Model model, @Valid @RequestBody LoginInput input) {
        return () -> {
            if (Strings.isNullOrEmpty(input.getLogin())
                    || Strings.isNullOrEmpty(input.getPassword())) {
                LogService.info("Requisicao de loggin invalida: " + new Gson().toJson(input));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            LogService.info("Requisicao de loggin valida: " + new Gson().toJson(input));
            LogService.info("Validando usuario: " + new Gson().toJson(input));
            JWTToken token = userService.logon(input.getLogin(), input.getPassword());
            if (Objects.nonNull(token)) {
                LogService.info("Usuario valido: " + new Gson().toJson(input));
                String payload = new Gson().toJson(token);
                HttpHeaders headers = new HttpHeaders();
                headers.add(JWTService.HEADER_STRING, JWTService.TOKEN_PREFIX + " " + JWTService.getToken(payload));
                return ResponseEntity.status(HttpStatus.OK).headers(headers).body(token);
            }
            LogService.info("Usuario invalido: " + new Gson().toJson(input));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        };
    }
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Callable<ResponseEntity> user(@RequestHeader(value="Authorization", defaultValue="") String authorization) {
        return () -> {
            if(Strings.isNullOrEmpty(authorization)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            LogService.info("Token: " + authorization);
            String payload = JWTService.getPayload(authorization);
            LogService.info("Payload: " + payload);
            if(Objects.nonNull(payload)) {
                Gson gson = new Gson();
                JWTToken token = gson.fromJson(payload, JWTToken.class);
                if(Objects.nonNull(token)) {
                    return ResponseEntity.status(HttpStatus.OK).body(token);
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        };
    }
}
