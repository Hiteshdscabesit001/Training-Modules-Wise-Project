package com.micro.accounts.service.impl;

import com.micro.accounts.constants.AccountsConstant;
import com.micro.accounts.dto.AccountsDto;
import com.micro.accounts.dto.CustomerDto;
import com.micro.accounts.entity.Accounts;
import com.micro.accounts.entity.Customer;
import com.micro.accounts.exception.CustomerAlreadyExistsException;
import com.micro.accounts.exception.ResourceNotFoundException;
import com.micro.accounts.mapper.AccountsMapper;
import com.micro.accounts.mapper.CustomerMapper;
import com.micro.accounts.repository.AccountsRepository;
import com.micro.accounts.repository.CustomerRepository;
import com.micro.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already register with given mobileNumber");
        }
//        customer.setCreatedAt(LocalDate.from(LocalDateTime.now()));
//        customer.setCreatedBy("SomeOne");

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }


    // customer created
    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000 + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstant.SAVINGS);
        newAccount.setBranchAddress(AccountsConstant.ADDRESS);
//        newAccount.setCreatedAt(LocalDate.from(LocalDateTime.now()));
//        newAccount.setCreatedBy("SomeOne");
        return newAccount;
    }

    // get data with mobileNumber
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    // update details
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account","CustomerID",accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer","CustomerID",customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }

    // delete account
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customr","mobileNumber",mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

}
