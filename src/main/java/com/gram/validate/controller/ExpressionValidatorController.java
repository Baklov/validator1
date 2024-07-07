package com.gram.validate.controller;

import com.gram.validate.service.ExpressionValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ExpressionValidatorController {

    private final ExpressionValidatorService validatorService;

    public ExpressionValidatorController(ExpressionValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateExpression(@RequestBody String expression) {
        boolean isValid = validatorService.validate(expression);
        if (isValid) {
            return ResponseEntity.ok("Valid expression");
        } else {
            return ResponseEntity.badRequest().body("Invalid expression");
        }
    }

}

