package com.fernandocanabarro.desafio_fcamara_books.spring_batch.step.reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import com.fernandocanabarro.desafio_fcamara_books.entities.Copy;
import com.fernandocanabarro.desafio_fcamara_books.entities.Loan;
import com.fernandocanabarro.desafio_fcamara_books.entities.User;
import com.fernandocanabarro.desafio_fcamara_books.repositories.CopyRepository;
import com.fernandocanabarro.desafio_fcamara_books.repositories.UserRepository;

@Configuration
public class ReadUsersWithLoansCloseToReturnReaderConfig {

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CopyRepository copyRepository;

    @Bean
    public JdbcCursorItemReader<Loan> readUsersWithLoansCloseToReturnReader(@Qualifier("appLibraryDB") DataSource dataSource){
        return new JdbcCursorItemReaderBuilder<Loan>()
            .name("readUsersWithLoansCloseToReturnReader")
            .dataSource(dataSource)
            .sql("""
                   SELECT l.id,l.copy_id,l.user_id,l.loan_date,l.date_to_return
                   FROM loans AS l
                   WHERE l.returned = 0
                   AND DATE_SUB(l.date_to_return,INTERVAL 1 DAY) = CURDATE()
                    """)
            .rowMapper(rowMapper())
            .build();
    }

    private RowMapper<Loan> rowMapper() {
        return new RowMapper<Loan>() {

            @Override
            public Loan mapRow(ResultSet rs, int rowNum) throws SQLException {
                Loan loan = new Loan();
                User user = userRepository.findById(rs.getLong("user_id")).get();
                Copy copy = copyRepository.findById(rs.getLong("copy_id")).get();
                loan.setId(rs.getLong("id"));
                loan.setCopy(copy);
                loan.setUser(user);
                loan.setLoanDate(LocalDate.parse(rs.getDate("loan_date").toString(),dtf));
                loan.setDateToReturn(LocalDate.parse(rs.getDate("date_to_return").toString(),dtf));
                return loan;
            }
        };
    }
}
