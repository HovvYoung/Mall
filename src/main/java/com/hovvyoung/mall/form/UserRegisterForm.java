package com.hovvyoung.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterForm {

    //@NotEmpty (used for set);
    @NotBlank(message = "cannot be empty") //(used for String, detect space)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;
}
