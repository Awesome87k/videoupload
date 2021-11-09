package com.genesislabs.video.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommonListDTO<T> {
    private List<T> data;
}