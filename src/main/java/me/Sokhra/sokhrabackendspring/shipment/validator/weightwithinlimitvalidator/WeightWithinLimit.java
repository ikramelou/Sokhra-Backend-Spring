package me.Sokhra.sokhrabackendspring.shipment.validator.weightwithinlimitvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsWeightWithinLimit.class)
public @interface WeightWithinLimit {
  String message() default "Input weight exceeds available weight";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
