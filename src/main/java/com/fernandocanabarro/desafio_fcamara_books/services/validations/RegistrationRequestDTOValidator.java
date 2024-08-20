package com.fernandocanabarro.desafio_fcamara_books.services.validations;

import com.fernandocanabarro.desafio_fcamara_books.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.exceptions.FieldMessage;
import com.fernandocanabarro.desafio_fcamara_books.entities.User;
import com.fernandocanabarro.desafio_fcamara_books.repositories.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class RegistrationRequestDTOValidator implements ConstraintValidator<RegistrationRequestDTOValid,RegistrationRequestDTO>{

    private final UserRepository userRepository;

    @Override
    public void initialize(RegistrationRequestDTOValid ann){}

    @Override
    public boolean isValid(RegistrationRequestDTO dto, ConstraintValidatorContext context) {
        List<FieldMessage> errors = new ArrayList<>();

        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isPresent()) {
            errors.add(new FieldMessage("email", "Este Email já Existe"));
        }

        Optional<User> userCpf = userRepository.findByCpf(dto.getCpf());
        if (userCpf.isPresent()) {
            errors.add(new FieldMessage("cpf", "Este CPF já existe"));
        }

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
