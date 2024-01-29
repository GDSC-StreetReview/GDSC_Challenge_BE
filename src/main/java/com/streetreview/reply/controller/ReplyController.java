package com.streetreview.comment.controller;

import com.streetreview.comment.service.CommentService;
import com.streetreview.common.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Component
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/write") // 댓글 작성
    public ResponseEntity(Message) writeComment(@RequestBody )
}
