package com.security.Spring_Security.Service;


import com.security.Spring_Security.Dto.SignupRequest;
import com.security.Spring_Security.Model.Customer;
import com.security.Spring_Security.Repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean createCustomer(SignupRequest signupRequest) {

        // check if customer already exist
        if (customerRepository.existsByEmail(signupRequest.getEmail()))
        {
            return false;
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(signupRequest,customer);
        // for password encoder
        String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
        customer.setPassword(hashPassword);
        customerRepository.save(customer);
        return true;
    }
}
