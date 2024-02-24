package com.hubspot.dto;

import com.hubspot.entity.SignUser;
import lombok.Data;


@Data
public class RoleDto {

    private String role;
    private SignUser user;
}
