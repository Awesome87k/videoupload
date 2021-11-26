package com.uploadservice.common;

/**
 * API ERROR Detail Message 양식
 */
public enum DetailMessage {
    /**
     * requestBody 미존재
     */
    handleMessageNotReadable {
        @Override
        public String toMessage() {
            return "RequestBody missing.";
        }
    },
    /**
     * 필수 requestBodyParam Missing
     */
    EssentialBodyParamMissing {
        @Override
        public String toMessage(String param) {
            return "'" + param + "' 파라미터 누락입니다.";
        }
    },
    /**
     * requestBodyParam 빈값
     */
    EssentialBodyParamBlank {
        @Override
        public String toMessage(String param) {
            return "Essential BodyParameter " + param + " is blank.";
        }
    },
    /**
     * 자원 중복
     */
    existedResource {
        @Override
        public String toMessage(String target) {
            return target + " data is already exsited.";
        }
    },
    /**
     * 리소스 not found
     */
    NotFoundResource {
        @Override
        public String toMessage(String target) {
            return target + " Resource cannot be found.";
        }
    },
    /**
     * DB 삽입 실패
     */
    dbInsertFail {
        @Override
        public String toMessage() {
            return "생성에 실패하였습니다.";
        }

    },
    /**
     * DB 업데이트 실패
     */
    dbUpdateFail {
        @Override
        public String toMessage() {
            return "변경에 실패하였습니다.";
        }

    },
    /**
     * DB 삭제 실패
     */
    dbDeleteFail {
        @Override
        public String toMessage() {
            return "삭제에 실패하였습니다.";
        }

    },
    INTERNAL_SERVER_ERROR {
        @Override
        public String toMessage() {
            return "서버 에러. 관리자에게 문의바랍니다.";
        }
    }
    ,
    /**
     * 파일업로드 실패
     */
    UploadFail {
        @Override
        public String toMessage() {
            return "데이터 처리중 오류가 발생하였습니다.";
        }

    }
    ;

    public String toMessage() {
        return super.toString();
    }

    public String toMessage(String target) {
        return super.toString();
    }
}
