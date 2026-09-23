package com.ailearning.userservice.service;

import com.ailearning.userservice.dto.request.RegisterUserRequest;
import com.ailearning.userservice.dto.request.UpdateUserRequest;
import com.ailearning.userservice.dto.response.RegisterUserResponse;
import com.ailearning.userservice.dto.response.UserProfileResponse;

public interface UserService {

    RegisterUserResponse register(RegisterUserRequest request);

    void deleteUser(Long id);

    void updateUser(Long id, UpdateUserRequest request);

    UserProfileResponse getUserProfile(Long id);


}
