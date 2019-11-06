package com.izicash.system.controller.api;

import com.izicash.system.response.VersionOutput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
//@Api(value = "api")
public class ApiController {

    @Value("${app.name}")
    private String systemName;
    @Value("${app.version}")
    private String systemVersion;
    @Value("${app.build}")
    private String systemBuild;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity index(Model model) {
        VersionOutput reponse = new VersionOutput();
        reponse.setSystem(systemName);
        reponse.setVersion(systemVersion);
        reponse.setBuild(systemBuild);
        return ResponseEntity.status(HttpStatus.OK).body(reponse);
    }
}
