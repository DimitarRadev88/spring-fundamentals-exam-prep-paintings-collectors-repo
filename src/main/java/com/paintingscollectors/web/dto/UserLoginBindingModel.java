package com.paintingscollectors.web.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginBindingModel {

    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    private String username;
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String password;

}
