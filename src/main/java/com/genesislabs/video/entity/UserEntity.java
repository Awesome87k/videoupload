package com.genesislabs.video.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name = "v_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vu_idx;
    @NotNull
    private String vu_id;
    @NotNull
    private String vu_pw;
    @NotNull
    private String vu_email;
    @NotNull
    private String vu_name;
    @NotNull
    private String vu_phonenumber;
    @OneToMany(mappedBy = "v_fileupload")
    private List<FileUploadEntity> employees;
}
