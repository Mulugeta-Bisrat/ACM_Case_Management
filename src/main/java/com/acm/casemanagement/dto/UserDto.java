package com.acm.casemanagement.dto;



import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserDto {
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private boolean isActive= true;
}

