package com.uploadservice.video.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EditUserInfoReqDTO {
    @NotBlank(message = "이름은 필수값 입니다.")
    private String name;
    @NotBlank(message = "전화번호는 필수값 입니다.")
    private String phonenum;
    @NotBlank(message = "레벨은 필수값 입니다.")
    private String level;
}