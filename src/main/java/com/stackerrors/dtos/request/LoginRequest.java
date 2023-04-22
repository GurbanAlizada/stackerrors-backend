package com.stackerrors.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {


    @NotBlank
    private String username;

    @NotBlank
    private String password;


}
