package com.genesislabs.common.enums;

import lombok.Getter;

@Getter
public enum URIEnum {
    ADMIN_MAIN_URL_REDIRECT("/page/video-search-view"),
    MEMBER_MAIN_URL_REDIRECT("/page/video-upload-view");

    private String message;

    URIEnum(String message) {
        this.message = message;
    }
}