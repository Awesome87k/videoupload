package com.uploadservice.common;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse {
    private boolean result;
    private String message;
    private Object content;
}