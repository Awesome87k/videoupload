package com.uploadservice.common.enums;

import lombok.Getter;

@Getter
public enum MainURIEnum {
    ADMIN_MAIN_URL_REDIRECT("/page/video-search-view"),
    MEMBER_MAIN_URL_REDIRECT("/page/video-search-view");

    private String message;

    MainURIEnum(String message) {
        this.message = message;
    }
}