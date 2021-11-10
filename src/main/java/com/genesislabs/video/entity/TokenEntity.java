package com.genesislabs.video.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
