package com.streetreview.reply.repository;

import com.streetreview.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    void deleteByReplyIdAndMember_memberId(Long replyId, Long memberId);
    Optional<Reply> findByReplyId(Long replyId);
}
