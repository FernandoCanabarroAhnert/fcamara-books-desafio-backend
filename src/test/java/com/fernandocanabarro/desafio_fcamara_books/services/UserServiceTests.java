package com.fernandocanabarro.desafio_fcamara_books.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import com.fernandocanabarro.desafio_fcamara_books.dtos.AccountActivationResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.EmailDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoginResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.NewPasswordDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.RegistrationRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.UserDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.ActivationCode;
import com.fernandocanabarro.desafio_fcamara_books.entities.Address;
import com.fernandocanabarro.desafio_fcamara_books.entities.PasswordRecover;
import com.fernandocanabarro.desafio_fcamara_books.entities.Role;
import com.fernandocanabarro.desafio_fcamara_books.entities.User;
import com.fernandocanabarro.desafio_fcamara_books.factories.RoleFactory;
import com.fernandocanabarro.desafio_fcamara_books.factories.UserFactory;
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
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.time.Instant;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private ActivationCodeRepository activationCodeRepository;

    @Mock
    private PasswordRecoverRepository passwordRecoverRepository;

    @Mock
    private CustomUserUtils customUserUtils;

    private RegistrationRequestDTO registrationRequestDTO;
    private Role roleUser;
    private Address address;
    private User user;
    private ActivationCode activationCode;
    private LoginRequestDTO loginRequestDTO;
    private Authentication authentication;
    private Jwt jwt;
    private PasswordRecover passwordRecover;
    private Mail passwordRecoverEmail;
    private NewPasswordDTO newPasswordDTO;

    @BeforeEach
    public void setup() throws Exception{
        registrationRequestDTO = UserFactory.createRegistrationRequestDTO();
        roleUser = RoleFactory.createRoleUser();
        address = UserFactory.createAddress();
        user = UserFactory.createUser();
        activationCode = new ActivationCode(1L, "123456", LocalDateTime.now(), LocalDateTime.now().plusMinutes(30L), null, user);
        loginRequestDTO = UserFactory.createLoginRequestDTO();
        authentication = new UsernamePasswordAuthenticationToken("alex@gmail.com", "123456");
        jwt = Jwt.withTokenValue("token")
                .headers(headers -> {
                    headers.put("alg", "HS256");
                    headers.put("typ", "JWT");
                })
                .issuer("login-auth-api")
                .subject(authentication.getName())
                .claims(claims -> {
                    claims.put("username", authentication.getName());
                    claims.put("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
                })
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(86400))
                .build();
        passwordRecover = new PasswordRecover(1L,"token","bob@gmail.com",Instant.now());
        passwordRecoverEmail = new Mail(new Email("ahnertfernando499@gmail.com"), "Recuperação de Senha",
             new Email("bob@gmail.com"), new Content("text/html", "teste"));
        newPasswordDTO = new NewPasswordDTO("token", "123456aZ@123");
    }

    @Test
    public void registerShouldReturnUserDTOWhenDataIsValid(){
        when(passwordEncoder.encode("123456aZ@")).thenReturn("$2a$10$iZJwrJbGrLiByGwD/eiE0u/SPiICKFbVmTLTfVfM34duTf7zU/C/m");
        when(roleRepository.findByAuthority("ROLE_USER")).thenReturn(roleUser);
        when(addressService.getAddressFromCep(registrationRequestDTO.getAddress())).thenReturn(address);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO response = userService.registrate(registrationRequestDTO);

        assertThat(response).isNotNull();
        assertThat(response.getFullName()).isEqualTo("Bob Grey");
        assertThat(response.getCpf()).isEqualTo("668.953.524-06");
        assertThat(response.getEmail()).isEqualTo("bob@gmail.com");
    }

    @Test
    public void activateAccountShouldReturnAccountActivationResponseDTOWhenCodeIsValid(){
        when(activationCodeRepository.findByCode("123456")).thenReturn(Optional.of(activationCode));
        when(userRepository.findByEmail("bob@gmail.com")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(activationCodeRepository.save(any(ActivationCode.class))).thenReturn(activationCode);

        AccountActivationResponseDTO response = userService.activateAccount("123456");

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("Conta Ativada com Sucesso");
    }

    @Test
    public void activateAccountShouldThrowResourceNotFoundExceptionWhenCodeDoesNotExist(){
        when(activationCodeRepository.findByCode("123456")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.activateAccount("123456")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void activateAccountShouldThrowExpiredCodeExceptionWhenCodeIsExpired(){
        activationCode.setExpiresAt(LocalDateTime.now().minusMinutes(1L));
        when(activationCodeRepository.findByCode("123456")).thenReturn(Optional.of(activationCode));
        

        assertThatThrownBy(() -> userService.activateAccount("123456")).isInstanceOf(ExpiredCodeException.class);
    }

    @Test
    public void loginShouldReturnLoginResponseDTOWhenUserExists(){
        user.setActivated(true);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(userRepository.findByEmail("alex@gmail.com")).thenReturn(Optional.of(user));
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        LoginResponseDTO response = userService.login(loginRequestDTO);

        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo(jwt.getTokenValue());
    }

    @Test
    public void loginShouldThrowAccountNotActivatedExceptionWhenAccountIsNotActivated(){
        user.setActivated(false);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(userRepository.findByEmail("alex@gmail.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.login(loginRequestDTO)).isInstanceOf(AccountNotActivatedException.class);
    }

    @Test
    public void authenticatedShouldReturnUserWhenUserExists(){
        when(customUserUtils.getLoggedUsername()).thenReturn("bob@gmail.com");
        when(userRepository.findByEmail("bob@gmail.com")).thenReturn(Optional.of(user));

        User response = userService.authenticated();

        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("bob@gmail.com");
        assertThat(response.getFullName()).isEqualTo("Bob Grey");
    }

    @Test
    public void authenticateShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist(){
        when(customUserUtils.getLoggedUsername()).thenThrow(ClassCastException.class);
        assertThatThrownBy(() -> userService.authenticated()).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    public void createRecoverTokenShouldDoNothingWhenUserExists(){
        EmailDTO emailDTO = new EmailDTO("bob@gmail.com");
        when(userRepository.findByEmail("bob@gmail.com")).thenReturn(Optional.of(user));
        when(passwordRecoverRepository.save(any(PasswordRecover.class))).thenReturn(passwordRecover);
        when(emailService.createPasswordRecoverEmail(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(passwordRecoverEmail);

        assertThatCode(() -> userService.createRecoverToken(emailDTO)).doesNotThrowAnyException();
    }

    @Test
    public void createRecoverTokenShouldThrowResourceNotFoundExceptionWhenEmailDoesNotExist(){
        EmailDTO emailDTO = new EmailDTO("bob@gmail.com");
        when(userRepository.findByEmail("bob@gmail.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.createRecoverToken(emailDTO)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void saveNewPasswordShouldDoNothingWhenTokenIsValid(){
        when(passwordRecoverRepository.searchValidToken(eq(newPasswordDTO.getToken()),any(Instant.class))).thenReturn(Optional.of(passwordRecover));
        when(userRepository.findByEmail("bob@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("123456aZ@123")).thenReturn("$2a$10$iZJwrJbGrLiByGwD/eiE0u/SPiICKFbVmTLTfVfM34duTf7zU/C/m");
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertThatCode(() -> userService.saveNewPassword(newPasswordDTO)).doesNotThrowAnyException();
    }

    @Test
    public void saveNewPasswordShouldThrowResourceNotFoundExceptionWhenTokenIsInvalid(){
        when(passwordRecoverRepository.searchValidToken(eq(newPasswordDTO.getToken()),any(Instant.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.saveNewPassword(newPasswordDTO)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void getMeShouldReturnUserDTO(){
        UserService spyService = Mockito.spy(userService);
        when(customUserUtils.getLoggedUsername()).thenReturn("bob@gmail.com");
        when(userRepository.findByEmail("bob@gmail.com")).thenReturn(Optional.of(user));
        when(spyService.authenticated()).thenReturn(user);

        UserDTO response = spyService.getMe();

        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("bob@gmail.com");
        assertThat(response.getFullName()).isEqualTo("Bob Grey");
        assertThat(response.getCpf()).isEqualTo("668.953.524-06");
    }


}
