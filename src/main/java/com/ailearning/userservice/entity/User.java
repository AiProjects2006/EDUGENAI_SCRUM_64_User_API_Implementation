package com.ailearning.userservice.entity;

import com.ailearning.userservice.enums.Role;
import com.ailearning.userservice.enums.UserStatus;
import jakarta.persistence.*;
        import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

        import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @NotBlank
    @Size(min = 8, max = 255)
    @Column(nullable = false)
    private String password;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "is_email_verified")
    private Boolean emailVerified = false;

    @Size(max = 10)
    @Column(name = "contact_number")
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private UserStatus accountStatus = UserStatus.PENDING_VERIFICATION;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}