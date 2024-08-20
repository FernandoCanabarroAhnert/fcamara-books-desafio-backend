package com.fernandocanabarro.desafio_fcamara_books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fernandocanabarro.desafio_fcamara_books.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{

}
