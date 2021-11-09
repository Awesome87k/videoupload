package com.genesislabs.video.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CJChListResDTO {
    private int cj_idx;
    private String ch_stat;
    private int ch_sec;
    private String ch_tvingid;
    private String ch_viewnm;
    private char ch_playertype;
    private String ch_outurl;
    private long sg_idx;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sg_start;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sg_end;
}