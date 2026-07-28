package com.ailearning.userservice.service;

import com.ailearning.userservice.dto.request.RegisterUserRequest;
import com.ailearning.userservice.dto.response.RegisterUserResponse;

public interface UserService {

    RegisterUserResponse register(RegisterUserRequest request);

    void deleteUser(Long id);

}
