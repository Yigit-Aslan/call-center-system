package com.yigitaslan.call_center_system.service.impl;

import com.yigitaslan.call_center_system.model.TicketComment;
import com.yigitaslan.call_center_system.repository.ITicketCommentRepository;
import com.yigitaslan.call_center_system.service.ITicketCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketCommentServiceImpl implements ITicketCommentService{
    private final ITicketCommentRepository ticketCommentRepository;

    public TicketCommentServiceImpl(ITicketCommentRepository _ticketCommentRepository){
        ticketCommentRepository = _ticketCommentRepository;
    }

    public List<TicketComment> getAllTicketComment()
    {
        return  ticketCommentRepository.findAll();
    }
    public Optional<TicketComment> getTicketCommentById(Long id)
    {
        return  ticketCommentRepository.findById(id);
    }

    public TicketComment createTicketComment(TicketComment comment)
    {
        LocalDateTime now = LocalDateTime.now();
        comment.setCreateDate(now);

        return ticketCommentRepository.save(comment);
    }
}
