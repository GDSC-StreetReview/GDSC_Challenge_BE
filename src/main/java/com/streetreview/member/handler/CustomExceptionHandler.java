package com.streetreview.member.handler;

import com.streetreview.util.SlackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@RequiredArgsConstructor
@ControllerAdvice
public class CustomExceptionHandler {
    private final SlackMessage slackMessage;

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException e) {

        slackMessage.sendNotification(makeMessage(e));
        e.printStackTrace();
        return ErrorResponseEntity.toResponseEntity(e.getAuthCode());
    }

    @ExceptionHandler(Exception.class)
    protected void handleException(Exception e) {
        slackMessage.sendNotification(makeMessage(e));
        e.printStackTrace();
    }

    private String makeMessage(CustomException e) {
        StringBuffer sb = new StringBuffer();
        sb.append("*에러 발생*: _<!channel> ");
        sb.append("\n\n```\n code : " + e.getAuthCode() + "\n```");
        sb.append("\n\n```\n reason : " + e.getLocalizedMessage() + "\n```");

        return sb.toString();
    }

    private String makeMessage(Exception e) {
        StringBuffer sb = new StringBuffer();
        sb.append("*핸들링 안된 에러 발생*: _<!channel> ");
        sb.append("\n\n```\n reason : " + e.getClass() + "\n``` \n");
        sb.append("*해당 Exception을 처리 해주세요* ");

        return sb.toString();
    }


}