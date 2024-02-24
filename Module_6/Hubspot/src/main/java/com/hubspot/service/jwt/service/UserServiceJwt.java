package com.hubspot.service.jwt.service;


import com.hubspot.dto.SignUpUserDto;
import com.hubspot.entity.SignUser;
import com.hubspot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceJwt implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // fetch customer from db
        SignUser user = (SignUser) userRepository.findByEmail(email);
        // .orElseThrow(() -> new UsernameNotFoundException("Customer not found with this email" + email));
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

    public Optional<Object> getCustomerByEmail(String email) {

        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public List<SignUser> getAllCustomers() {

        return userRepository.findAll();
    }

    public SignUser updateCustomerByEmail(String email, SignUpUserDto userDto) throws AttributeNotFoundException {

        Optional<Object> optionalCustomer = Optional.ofNullable(userRepository.findByEmail(email));

        if (optionalCustomer.isPresent()) {
            SignUser updatedUser = (SignUser) optionalCustomer.get();
            updatedUser.setEmail(userDto.getEmail());
            updatedUser.setFirstName(userDto.getFirstName());
            updatedUser.setLastName(userDto.getLastName());
            updatedUser.setPassword(encoder.encode(userDto.getPassword()));
            updatedUser.setGender(userDto.getGender());
            updatedUser.setRole(userDto.getRole());
            updatedUser.setPhoneNumber(userDto.getPhoneNumber());
            return userRepository.save(updatedUser);
        } else {
            throw new AttributeNotFoundException("Customer is not present with this email" + email);
        }

    }


}
