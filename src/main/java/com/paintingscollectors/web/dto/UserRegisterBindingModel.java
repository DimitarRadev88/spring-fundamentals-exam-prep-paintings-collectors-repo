package com.paintingscollectors.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterBindingModel {

    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters")
    private String username;
    @Email(message = "Please enter valid email!")
    @NotNull(message = "Email cannot be empty!")
    private String email;
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String password;
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String confirmPassword;

}




