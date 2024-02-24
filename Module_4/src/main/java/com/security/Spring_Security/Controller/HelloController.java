package com.security.Spring_Security.Controller;

import com.security.Spring_Security.Dto.CustomerDto;
import com.security.Spring_Security.Model.Customer;
import com.security.Spring_Security.Service.jwt.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.AttributeNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HelloController {

    private final CustomerServiceImpl customerService;

    @Autowired
    public HelloController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from authorized API request";
    }


    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{email}")
    public Optional<Customer> getCustomerByEmail(@PathVariable String email)
    {
        return customerService.getCustomerByEmail(email);
    }

    @PutMapping("/update/{email}")
    public Customer updateCustomerByEmail(@PathVariable String email,@RequestBody CustomerDto customerDto) throws AttributeNotFoundException {
        return customerService.updateCustomerByEmail(email,customerDto);

    }

//    @PutMapping("/{email}")
//    public ResponseEntity<Customer> updateCustomerByEmail(@PathVariable String email, @RequestBody Customer updatedUser) {
//        return customerService.updateCustomerByEmail(email)
//                .map(customer -> {
//                    customer.setEmail(updatedUser.getEmail());
//                    customer.setName(updatedUser.getName);
//                    userRepository.save(user);
//                    return ResponseEntity.ok().body(user);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
}
