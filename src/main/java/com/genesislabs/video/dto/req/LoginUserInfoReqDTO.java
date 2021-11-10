package com.genesislabs.video.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfoReqDTO {
    private String email;
    private String password;
}