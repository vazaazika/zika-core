package br.les.opus.gamification.validators;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target( { ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueTeamNameValidator.class)
@Documented
public @interface UniqueTeamName {

    String message() default "{br.les.opus.gamification.unique.team.name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}