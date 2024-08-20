package com.fernandocanabarro.desafio_fcamara_books.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.LoanReturnResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.MyLoansDTO;
import com.fernandocanabarro.desafio_fcamara_books.entities.Copy;
import com.fernandocanabarro.desafio_fcamara_books.entities.Loan;
import com.fernandocanabarro.desafio_fcamara_books.entities.User;
import com.fernandocanabarro.desafio_fcamara_books.factories.CopyFactory;
import com.fernandocanabarro.desafio_fcamara_books.factories.LoanFactory;
import com.fernandocanabarro.desafio_fcamara_books.factories.UserFactory;
import com.fernandocanabarro.desafio_fcamara_books.repositories.CopyRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.LoanRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.UserRepository;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.ResourceNotFoundException;
import com.fernandocanabarro.desafio_fcamara_books.services.exceptions.UnableToBorrowException;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTests {

    @InjectMocks
    private LoanService loanService;
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private UserService userService;
    @Mock
    private CopyRepository copyRepository;
    @Mock
    private UserRepository userRepository;

    private LoanRequestDTO loanRequestDTO;
    private User user;
    private Copy copy;
    private Loan loan;

    @BeforeEach
    public void setup() throws Exception{
        loanRequestDTO = new LoanRequestDTO(1L);
        user = UserFactory.createUser();
        copy = CopyFactory.createCopy();
        loan = LoanFactory.createLoan();
    }

    @Test
    public void makeLoanShouldReturnLoanResponseDTOWhenDataIsValid(){
        user.setActivated(true);
        user.setLateReturn(0);

        when(userService.authenticated()).thenReturn(user);
        when(copyRepository.findById(1L)).thenReturn(Optional.of(copy));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        LoanResponseDTO response = loanService.makeLoan(loanRequestDTO);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUser().getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getCopy().getId()).isEqualTo(copy.getId());
    }

    @Test
    public void makeLoanShouldThrowUnableToBorrowExceptionWhenUserHasMoreThan2LateReturns(){
        user.setActivated(true);
        user.setLateReturn(3);

        when(userService.authenticated()).thenReturn(user);
        when(copyRepository.findById(1L)).thenReturn(Optional.of(copy));

        assertThatThrownBy(() -> loanService.makeLoan(loanRequestDTO)).isInstanceOf(UnableToBorrowException.class);
    }

    @Test
    public void makeLoanShouldThrowUnableToBorrowExceptionWhenUserAlreadyHasTheCopy(){
        user.setActivated(true);
        user.setLateReturn(0);
        user.addLoan(loan);

        when(userService.authenticated()).thenReturn(user);
        when(copyRepository.findById(1L)).thenReturn(Optional.of(copy));

        assertThatThrownBy(() -> loanService.makeLoan(loanRequestDTO)).isInstanceOf(UnableToBorrowException.class);
    }

    @Test
    public void makeLoanShouldThrowResourceNotFoundExceptionWhenCopyIdDoesNotExists(){
        user.setActivated(true);
        user.setLateReturn(0);

        when(userService.authenticated()).thenReturn(user);
        when(copyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.makeLoan(loanRequestDTO)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void findMyLoansShouldReturnPageOfMyLoansDTO(){
        Page<Loan> page = new PageImpl<>(List.of(loan));
        Pageable pageable = PageRequest.of(0, 10);

        user.addLoan(loan);
        when(userService.authenticated()).thenReturn(user);
        when(loanRepository.findByUser(pageable, user)).thenReturn(page);

        Page<MyLoansDTO> response = loanService.findMyLoans(pageable);

        assertThat(response).isNotEmpty();
        assertThat(response.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getCopy().getId()).isEqualTo(loan.getCopy().getId());
        assertThat(response.getContent().get(0).getDateToReturn()).isEqualTo(loan.getDateToReturn());
        assertThat(response.getContent().get(0).getLoanDate()).isEqualTo(loan.getLoanDate());
    }

    @Test
    public void returnLoanShouldReturnLoanReturnResponseDTOWhenDataIsValid(){
        loan.setReturned(false);
        user.addLoan(loan);
        when(userService.authenticated()).thenReturn(user);
        when(copyRepository.findById(1L)).thenReturn(Optional.of(copy));

        LoanReturnResponseDTO response = loanService.returnLoan(loanRequestDTO);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("Cópia retornada com Sucesso");
    }

    @Test
    public void returnLoanShouldThrowResourceNotFoundExceptionWhenUserDoesNotHaveALoanActiveWithThatCopy(){
        when(userService.authenticated()).thenReturn(user);
        when(copyRepository.findById(1L)).thenReturn(Optional.of(copy));

        assertThatThrownBy(() -> loanService.returnLoan(loanRequestDTO)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void returnLoanShouldThrowResourceNotFoundExceptionWhenCopyDoesNotExist(){
        when(userService.authenticated()).thenReturn(user);
        when(copyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.returnLoan(loanRequestDTO)).isInstanceOf(ResourceNotFoundException.class);
    }
    
    @Test
    public void findAllLoansShouldReturnPageOfLoanDTO(){
        Page<Loan> page = new PageImpl<>(List.of(loan));
        Pageable pageable = PageRequest.of(0, 10);

        when(loanRepository.findAll(pageable)).thenReturn(page);

        Page<LoanDTO> response = loanService.findAllLoans(pageable);

        assertThat(response).isNotEmpty();
        assertThat(response.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getCopy().getId()).isEqualTo(loan.getCopy().getId());
        assertThat(response.getContent().get(0).getDateToReturn()).isEqualTo(loan.getDateToReturn());
        assertThat(response.getContent().get(0).getLoanDate()).isEqualTo(loan.getLoanDate());
    }
}
