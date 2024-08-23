package com.nc13.study.board.service;

import com.nc13.study.board.domain.Reply;
import com.nc13.study.board.domain.ReplyRepository;
import com.nc13.study.board.dto.ReplyDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepo;

    @Transactional
    public void save(ReplyDTO reply) {
        replyRepo.save(reply.toEntity());
    }

    @Transactional
    public List<ReplyDTO> findAllByBoardId(Long boardId) {
        List<Reply> replyList = replyRepo.findAllByBoardId(boardId);
        List<ReplyDTO> replies = new ArrayList<>();
        for (Reply reply : replyList) {
            replies.add(new ReplyDTO(reply));
        }
        return replies;
    }
}
