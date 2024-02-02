package com.streetreview.reply.controller;


import com.streetreview.common.dto.Message;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.reply.dto.ReqDeleteReplyDto;
import com.streetreview.reply.dto.ReqReplyListDto;
import com.streetreview.reply.dto.ReqWriteReplyDto;
import com.streetreview.reply.dto.ResReplyListDto;
import com.streetreview.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.streetreview.member.security.JwtInfoExtractor.getStrvMember;

@RestController
@RequiredArgsConstructor
@Component
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyServiceImpl;
    @PostMapping("/write") // 댓글 작성
    public ResponseEntity<Message> writeReply(@RequestBody ReqWriteReplyDto reqWriteReplyDto) {
        return ResponseEntity.ok(new Message(StatusCode.OK, replyServiceImpl.writeReply(reqWriteReplyDto, getStrvMember())));
    }
    @DeleteMapping
    public ResponseEntity<Message> deleteReply(@RequestBody ReqDeleteReplyDto reqDeleteReplyDto) {
        replyServiceImpl.deleteReply(reqDeleteReplyDto, getStrvMember());
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }

    @GetMapping
    public ResponseEntity<List<ResReplyListDto>> getReply(@RequestBody ReqReplyListDto reqReplyListDto) {
        List<ResReplyListDto> replyList = replyServiceImpl.getAllReplyList(reqReplyListDto.getReviewId());
        return ResponseEntity.ok(replyList);
    }
}
