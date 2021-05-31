package com.tc.chapter1;

import com.tc.chapter1.Car;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Set;

public class CarTest {

    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void manufacturerIsNull() {
        Car car = new Car(null, "DD-AB-123", 4);
        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);
        assertEquals(1, constraintViolations.size());
        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void licensePlateTooShort() {
        Car car = new Car("Morris", "D", 4);
        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);
        assertEquals(1, constraintViolations.size());
        assertEquals("size must be between 2 and 14", constraintViolations.iterator().next().getMessage());
    }

}
