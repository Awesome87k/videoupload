package com.genesislabs.common.enums;

import lombok.Getter;

@Getter
public enum MainURIEnum {
    ADMIN_MAIN_URL_REDIRECT("/page/video-search-view"),
    MEMBER_MAIN_URL_REDIRECT("/page/video-upload-view");

    private String message;

    MainURIEnum(String message) {
        this.message = message;
    }
}