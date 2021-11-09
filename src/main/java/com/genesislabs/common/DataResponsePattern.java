package com.genesislabs.common;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataResponsePattern {
    public DataResponse mvcReponseSuccess(Object _contents) {
        return new DataResponse(true, "성공", _contents);
    }
    public DataResponse mvcReponseFail (String _msg) {
        return new DataResponse(false, _msg, null);
    }
}
