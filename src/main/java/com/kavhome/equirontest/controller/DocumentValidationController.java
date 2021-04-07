package com.kavhome.equirontest.controller;

import com.kavhome.equirontest.pojo.DocPojo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author <a href="mailto:koljuchkin.aleksandr@alphaopen.com" >Aleksandr Kolyuchkin</a>
 */
@RestController
//@Validated
public class DocumentValidationController {
    @PostMapping("/validate")
    ResponseEntity<String> validateDocument(@Valid @RequestBody DocPojo doc) {
        return ResponseEntity.ok("valid");
    }
}
