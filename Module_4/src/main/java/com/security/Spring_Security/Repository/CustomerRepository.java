package com.security.Spring_Security.Repository;

import com.security.Spring_Security.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer,Long>
{

    boolean existsByEmail(String email);


    Optional<Customer> findByEmail(String email);
}
