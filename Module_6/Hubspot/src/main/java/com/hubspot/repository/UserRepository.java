package com.hubspot.repository;

import com.hubspot.entity.SignUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SignUser,Long> {


    static UserDetails loadUserByUsername(String email) {
        return null;
    }

    SignUser findByEmail(String phoneNumber);

    Optional<SignUser> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

}
