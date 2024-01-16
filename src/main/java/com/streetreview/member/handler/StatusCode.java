package com.streetreview.member.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum StatusCode {
    OK(200, "OK", HttpStatus.OK),
    MALFORMED(400, "형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_FOUND (400, "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    FORBIDDEN(403, "해당 요청에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    UNAUTHORIZED (400, "로그인 후 이용가능합니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_JWT(400, "기존 토큰이 만료되었습니다. 해당 토큰을 가지고 /token/refresh 링크로 이동 후 토큰을 재발급 받으세요.", HttpStatus.UNAUTHORIZED),
    INVALID_DATA_FORMAT(400, "형식이 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    REGISTERED_EMAIL(400, "등록된 회원입니다.", HttpStatus.BAD_REQUEST),
    DORMANT_ACCOUNT(423, "이 계정은 휴먼 계정입니다.", HttpStatus.LOCKED),
    DISABLED_ACCOUNT(403, "삭제된 계정입니다.", HttpStatus.FORBIDDEN),
    USERNAME_NOT_FOUND(404, "가입된 이메일이 없습니다.", HttpStatus.NOT_FOUND),
    SLACK_ERROR_TEST(400, "앞으로 에러나면 여기에 출몰", HttpStatus.BAD_REQUEST),
    ALREADY_EXIST(400, "이미 존재하는 데이터입니다.", HttpStatus.BAD_REQUEST)
    ;
    @Getter
    private int statusCode;
    @Getter
    private String message;
    @Getter
    private HttpStatus status;

    StatusCode(int statusCode, String message, HttpStatus status) {
        this.statusCode = statusCode;
        this.message = message;
        this.status = status;
    }

    public String toString() {
        return "{ " +
                "\n\"code\" : " + "\""+ statusCode +"\"" +
                "\n\"status\" : " + "\""+status+"\"" +
                "\n\"message\" : " + "\""+message+"\"" +
                "\n}";
    }
}
