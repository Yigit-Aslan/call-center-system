package com.yigitaslan.call_center_system.controller;

import com.yigitaslan.call_center_system.model.TicketComment;
import com.yigitaslan.call_center_system.service.ITicketCommentService;
import com.yigitaslan.call_center_system.service.impl.TicketCommentServiceImpl;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ticketcomments")
public class TicketCommentController {
    private final ITicketCommentService ticketCommentService;

    public TicketCommentController(ITicketCommentService _ticketCommentService)
    {
        ticketCommentService = _ticketCommentService;
    }

    @GetMapping
    public ResponseEntity<List<TicketComment>> getAllTicketComments()
    {
        return ResponseEntity.ok(ticketCommentService.getAllTicketComment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketComment> getTicketCommentById(@PathVariable Long id)
    {
        return ticketCommentService.getTicketCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TicketComment> createTicketComment(@PathVariable TicketComment comment)
    {
        TicketComment createdComment = ticketCommentService.createTicketComment(comment);

        return ResponseEntity.ok(createdComment);
    }
}
