package com.ailearning.userservice.dto.response;
import com.ailearning.userservice.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponse {

    private String message;
    private UserStatus accountStatus;

}