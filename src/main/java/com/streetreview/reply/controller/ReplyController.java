package com.streetreview.reply.controller;


import com.streetreview.common.dto.Message;
import com.streetreview.member.handler.StatusCode;
import com.streetreview.reply.dto.*;
import com.streetreview.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.streetreview.member.security.JwtInfoExtractor.getStrvMember;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyServiceImpl;
    @PostMapping("/write") // 댓글 작성
    public ResponseEntity<Message> writeReply(@RequestBody ReqWriteReplyDto reqWriteReplyDto) {
        return ResponseEntity.ok(new Message(StatusCode.OK, replyServiceImpl.writeReply(reqWriteReplyDto, getStrvMember())));
    }
    @DeleteMapping("/delete/{replyId}") // 댓글 삭제
    public ResponseEntity<Message> deleteReply(@PathVariable(name = "replyId")Long replyId) {
        replyServiceImpl.deleteReply(replyId, getStrvMember());
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }
    @GetMapping("/{reviewId}")
    public ResponseEntity<Message> getReply(@PathVariable(name = "reviewId") Long reviewId) {
        List<ResReplyListDto> replyList = replyServiceImpl.getAllReplyList(reviewId);
        return ResponseEntity.ok(new Message(StatusCode.OK, replyList));
    }

    @PutMapping("/report") // 댓글 신고
    public ResponseEntity<Message> reportReply(@RequestBody ReqReportReplyDto reqReportReplyDto) {
        replyServiceImpl.reportReply(reqReportReplyDto, getStrvMember());
        return ResponseEntity.ok(new Message(StatusCode.OK));
    }

}
