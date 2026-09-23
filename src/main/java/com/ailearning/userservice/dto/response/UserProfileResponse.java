package com.ailearning.userservice.dto.response;

import com.ailearning.userservice.enums.Role;
import com.ailearning.userservice.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String contactNumber;
    private Role role;
    private UserStatus accountStatus;
}

