package com.genesislabs.video.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name = "v_fileupload")
public class FileUploadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vf_idx;
    @NotNull
    private Integer vu_idx;
    @NotNull
    private String vf_path;
    @NotNull
    private String vf_filename;
    @NotNull
    private Integer vf_filesize;
    @ManyToOne
    @JoinColumn(name = "v_fileupload_vu_idx")
    private UserEntity v_fileupload;
}
