package com.fernandocanabarro.desafio_fcamara_books.services.validations;

import com.fernandocanabarro.desafio_fcamara_books.dtos.NewPasswordDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.exceptions.FieldMessage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class NewPasswordDTOValidator implements ConstraintValidator<NewPasswordDTOValid,NewPasswordDTO>{

    @Override
    public void initialize(NewPasswordDTOValid ann){}

    @Override
    public boolean isValid(NewPasswordDTO dto, ConstraintValidatorContext context) {
        List<FieldMessage> errors = new ArrayList<>();

        String password = dto.getPassword();
        if (!Pattern.matches(".*[A-Z].*", password)) {
            errors.add(new FieldMessage("password", "A Senha deve possuir pelo menos uma Letra Maiúscula"));
        }
        if (!Pattern.matches(".*[a-z].*", password)) {
            errors.add(new FieldMessage("password", "A Senha deve possuir pelo menos uma Letra Minúscula"));
        }
        if (!Pattern.matches(".*[0-9].*", password)) {
            errors.add(new FieldMessage("password", "A Senha deve possuir pelo menos um Número"));
        }
        if (!Pattern.matches(".*[\\W].*", password)) {
            errors.add(new FieldMessage("password", "A Senha deve possuir pelo menos um Caractere Especial"));
        }

        for (FieldMessage f : errors){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.getMessage())
                .addPropertyNode(f.getFieldName())
                .addConstraintViolation();
        }

        return errors.isEmpty();
    }

}
