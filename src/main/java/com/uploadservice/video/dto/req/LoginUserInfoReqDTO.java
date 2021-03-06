package com.uploadservice.video.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfoReqDTO {
    @NotBlank(message = "이메일은 필수값 입니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수값 입니다.")
    private String password;
}