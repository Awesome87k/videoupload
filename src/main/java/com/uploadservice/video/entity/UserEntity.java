package com.uploadservice.video.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name = "v_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vu_idx;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String vu_email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String vu_pw;

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    private String vu_name;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String vu_phonenum;

    @NotBlank(message = "사용자 권한을 선택해주세요.")
    private String vu_level;

    @Column(insertable = false)
    private String vu_del_yn;
}
