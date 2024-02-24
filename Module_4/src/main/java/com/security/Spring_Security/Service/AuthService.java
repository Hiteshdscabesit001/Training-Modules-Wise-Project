package com.security.Spring_Security.Service;

import com.security.Spring_Security.Dto.SignupRequest;

public interface AuthService {
    boolean createCustomer(SignupRequest signupRequest);
}
