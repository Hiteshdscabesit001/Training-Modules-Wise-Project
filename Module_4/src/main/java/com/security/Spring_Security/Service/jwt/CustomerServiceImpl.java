package com.security.Spring_Security.Service.jwt;

import com.security.Spring_Security.Dto.CustomerDto;
import com.security.Spring_Security.Model.Customer;
import com.security.Spring_Security.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       // fetch customer from db
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with this email"+email));
        return new User(customer.getEmail(),customer.getPassword(), Collections.emptyList());
    }

    public Optional<Customer> getCustomerByEmail(String email) {

        return customerRepository.findByEmail(email);
    }

    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    public Customer updateCustomerByEmail(String email, CustomerDto customerDto) throws AttributeNotFoundException {

        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);

        if(optionalCustomer.isPresent())
        {
            Customer updatedCustomer = optionalCustomer.get();
            updatedCustomer.setEmail(customerDto.getEmail());
            updatedCustomer.setName(customerDto.getName());
            updatedCustomer.setPassword(customerDto.getPassword());
            return customerRepository.save(updatedCustomer);
        } else {
            throw new AttributeNotFoundException("Customer is not present with this email"+email);
        }

    }

  //  public ResponseEntity<Customer> updateCustomerByEmail(@PathVariable String email, @RequestBody Customer updatedUser) {
//        return customerService.updateCustomerByEmail(email)
//                .map(customer -> {
//                    customer.setEmail(updatedUser.getEmail());
//                    customer.setName(updatedUser.getName);
//                    userRepository.save(user);
//                    return ResponseEntity.ok().body(user);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }





//    public Product getProduct(int id) {
//        return productList.stream()
//                .filter(product -> product.getProductId() == id)
//                .findAny()
//                .orElseThrow(() -> new RuntimeException("product " + id + " not found"));
//    }
}
