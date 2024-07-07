package com.gram.validate;

import com.gram.validate.service.ExpressionValidatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ArithmeticExpressionValidationTest {

    @Autowired
    private ExpressionValidatorService validatorService;

    @Test
    void testValidExpressions() {
        assertTrue(validatorService.validate("1"));
        assertTrue(validatorService.validate("1234.5 - 543 * 2 + 11.99"));
        assertTrue(validatorService.validate("1.2 + 3"));
        assertTrue(validatorService.validate("1234.56 - 543 * 2 + 11.99"));
        assertTrue(validatorService.validate("12 + 34"));
        assertTrue(validatorService.validate("1.2 + 3"));
        assertTrue(validatorService.validate("1234.56 - 543 * 2 + 11.99"));

        assertTrue(validatorService.validate("(1 + 2)"));
        assertTrue(validatorService.validate("(((1 + 2)))"));
        assertTrue(validatorService.validate(" ( (   ( 1   +   2 ))    )"));
        assertTrue(validatorService.validate("((1 - 2) * (1 + 2) - 3) / 4"));
        assertTrue(validatorService.validate("(   1 + 2)* ( (    ( 3-4 *((11.99))  )  )) /4.4   "));
        assertTrue(validatorService.validate("   (   1 + 2)* (  3-4    )   "));
    }

    @Test
    void testInvalidExpressions() {
        assertFalse(validatorService.validate("()"));
        assertFalse(validatorService.validate("(   )"));
        assertFalse(validatorService.validate(" (   ) "));
        assertFalse(validatorService.validate(" ( ( ) ) "));

        assertFalse(validatorService.validate("23."));
        assertFalse(validatorService.validate("12 ."));
        assertFalse(validatorService.validate("1. + 2"));
        assertFalse(validatorService.validate("1.2.3 + 4"));

        assertFalse(validatorService.validate("1 2+3"));
        assertFalse(validatorService.validate("1 2 + 3"));
        assertFalse(validatorService.validate("1 (2 - 3)"));
        assertFalse(validatorService.validate("1 2"));

        assertFalse(validatorService.validate("1+"));
        assertFalse(validatorService.validate("1 + 2 +"));
        assertFalse(validatorService.validate("(1 + 2"));
        assertFalse(validatorService.validate("1 + 2)"));

        assertFalse(validatorService.validate(" 1 + @"));

        assertFalse(validatorService.validate("(   1 + 2)* (  3-4 *((11.99)   )   "));
        assertFalse(validatorService.validate("  ( (   1 + 2)* (  3-4    )   "));

    }

}
