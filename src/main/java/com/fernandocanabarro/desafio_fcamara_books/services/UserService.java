package com.fernandocanabarro.desafio_fcamara_books.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_fcamara_books.controllers.BookController;
import com.fernandocanabarro.desafio_fcamara_books.controllers.CopyController;
import com.fernandocanabarro.desafio_fcamara_books.controllers.LoanController;
import com.fernandocanabarro.desafio_fcamara_books.controllers.UserController;
import com.fernandocanabarro.desafio_fcamara_books.dtos.AccountActivationResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.AddressRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.EmailDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.NewPasswordDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.UserDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.ActivationCode;
import com.fernandocanabarro.desafio_fcamara_books.entities.Address;
import com.fernandocanabarro.desafio_fcamara_books.entities.PasswordRecover;
import com.fernandocanabarro.desafio_fcamara_books.entities.User;
import com.fernandocanabarro.desafio_fcamara_books.repositories.ActivationCodeRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.AddressRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.PasswordRecoverRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.RoleRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.UserRepository;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.AccountNotActivatedException;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ExpiredCodeException;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ResourceNotFoundException;
import com.fernandocanabarro.desafio_fcamara_books.utils.CustomUserUtils;
import com.sendgrid.helpers.mail.Mail;

import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final EmailService emailService;
    private final ActivationCodeRepository activationCodeRepository;
    private final PasswordRecoverRepository passwordRecoverRepository;
    private final CustomUserUtils customUserUtils;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Long jwtDuration = 86400L;

    @Transactional
    public UserDTO registrate(RegistrationRequestDTO dto) {
        User user = new User();
        toEntity(user, dto);
        user.setActivated(false);
        user = userRepository.save(user);
        sendConfirmationEmail(user);
        return new UserDTO(user);
    }

    private void toEntity(User user, RegistrationRequestDTO dto) {
        user.setFullName(dto.getFullName());
        user.setCpf(dto.getCpf());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setBirthDate(LocalDate.parse(dto.getBirthDate(), dtf));
        user.setLateReturn(0);
        user.setLoans(Arrays.asList());
        user.getRoles().clear();
        user.addRole(roleRepository.findByAuthority("ROLE_USER"));
        user.setAddress(findAndSaveAddress(dto.getAddress()));
    }

    @Transactional
    private Address findAndSaveAddress(AddressRequestDTO addressRequestDTO) {
        Address address = addressService.getAddressFromCep(addressRequestDTO);
        address = addressRepository.save(address);
        return address;
    }

    private void sendConfirmationEmail(User user) {
        String code = generateAndSaveActivationCode(user);
        Mail mail = emailService.createConfirmationEmailTemplate(user.getEmail(), user.getFullName(), code,
                "Ativação de Conta");
        emailService.sendEmail(mail);
    }

    private String generateAndSaveActivationCode(User user) {
        String generatedCode = generateCode();
        ActivationCode activationCode = new ActivationCode();
        activationCode.setCode(generatedCode);
        activationCode.setCreatedAt(LocalDateTime.now());
        activationCode.setExpiresAt(LocalDateTime.now().plusMinutes(30L));
        activationCode.setUser(user);
        activationCodeRepository.save(activationCode);
        return generatedCode;
    }

    private String generateCode() {
        String chars = "0123456789";
        StringBuilder builder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < chars.length(); i++) {
            int randomIndex = secureRandom.nextInt(chars.length());
            builder.append(chars.charAt(randomIndex));
        }
        return builder.toString();
    }

    @Transactional
    public AccountActivationResponseDTO activateAccount(String code) {
        ActivationCode savedCode = activationCodeRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Código Inválido"));
        if (!savedCode.getExpiresAt().isAfter(LocalDateTime.now())) {
            sendConfirmationEmail(savedCode.getUser());
            throw new ExpiredCodeException("O Código de Ativação expirou. Um novo Código será enviado para o email: "
                    + savedCode.getUser().getEmail());
        }
        User user = userRepository.findByEmail(savedCode.getUser().getEmail()).get();
        user.setActivated(true);
        userRepository.save(user);
        savedCode.setValidatedAt(LocalDateTime.now());
        activationCodeRepository.save(savedCode);
        return new AccountActivationResponseDTO("Conta Ativada com Sucesso")
                .add(linkTo(methodOn(UserController.class).login(null)).withRel("Efetuar Login"));
    }

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO dto) {
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(dto.getEmail(),
                dto.getPassword());
        Authentication response = authenticationManager.authenticate(authentication);
        User user = userRepository.findByEmail(dto.getEmail()).get();                                      
        if (!user.getActivated()) {
            throw new AccountNotActivatedException("A Conta ainda não foi Ativada");
        }
        var claims = JwtClaimsSet.builder()
                .issuer("login-auth-api")
                .subject(response.getName())
                .claim("username", response.getName())
                .claim("authorities", response.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                        .toList())
                .issuedAt(Instant.now())
                .expiresAt(expirationTime())
                .build();

        String jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponseDTO(jwt, jwtDuration)
                .add(linkTo(methodOn(BookController.class).findAll(null)).withRel("Consultar Livros"))
                .add(linkTo(methodOn(CopyController.class).findAllAvailableCopies(null))
                        .withRel("Consultar Cópias Disponíveis"))
                .add(linkTo(methodOn(LoanController.class).makeLoan(null)).withRel("Pegar Livro Emprestado"));
    }

    private Instant expirationTime() {
        return Instant.now().plusSeconds(jwtDuration);
    }

    @Transactional
    public User authenticated() {
        try {
            String username = customUserUtils.getLoggedUsername();
            return userRepository.findByEmail(username).get();
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Transactional
    public void createRecoverToken(EmailDTO obj) {
        User user = userRepository.findByEmail(obj.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email não Encontrado"));
        String token = UUID.randomUUID().toString();
        PasswordRecover passwordRecover = new PasswordRecover();
        passwordRecover.setToken(token);
        passwordRecover.setEmail(user.getEmail());
        passwordRecover.setExpiration(Instant.now().plusSeconds(1800));
        passwordRecover = passwordRecoverRepository.save(passwordRecover);
        Mail mail = emailService.createPasswordRecoverEmail(user.getEmail(), user.getFullName(), token,
                "Recuperação de Senha");
        emailService.sendEmail(mail);
    }

    @Transactional
    public void saveNewPassword(NewPasswordDTO obj) {
        PasswordRecover passwordRecover = passwordRecoverRepository.searchValidToken(obj.getToken(), Instant.now())
                .orElseThrow(() -> new ResourceNotFoundException("Token Inválido"));
        User user = userRepository.findByEmail(passwordRecover.getEmail()).get();
        user.setPassword(passwordEncoder.encode(obj.getPassword()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserDTO getMe() {
        return new UserDTO(authenticated());
    }

}
