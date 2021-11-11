package com.genesislabs.video.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RemoveUserReqDTO {
    @NotBlank(message = "이메일은 필수값 입니다.")
    private String email;
}