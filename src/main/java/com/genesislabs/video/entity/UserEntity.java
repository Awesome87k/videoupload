package com.genesislabs.video.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name = "v_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vu_idx;
    @NotEmpty
    private String vu_email;
    @NotEmpty
    private String vu_pw;
    @NotEmpty
    private String vu_name;
    @NotEmpty
    private String vu_phonenum;
    @NotEmpty
    private String vu_level;
    private String vu_del_yn;
}
