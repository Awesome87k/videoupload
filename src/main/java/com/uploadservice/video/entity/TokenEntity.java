package com.uploadservice.video.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name = "v_token")
public class TokenEntity {
    @Id
    private Integer vu_idx;
    @NotNull
    private String vt_refresh_token;
    @NotNull
    private Long vt_expiredtm;
}
