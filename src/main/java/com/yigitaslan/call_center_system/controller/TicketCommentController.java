package com.yigitaslan.call_center_system.controller;

import com.yigitaslan.call_center_system.model.TicketComment;
import com.yigitaslan.call_center_system.service.ITicketCommentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api/ticketcomments")
public class TicketCommentController {
    private final ITicketCommentService ticketCommentService;
    private final Executor apiExecutor;

    public TicketCommentController(ITicketCommentService _ticketCommentService, @Qualifier("apiExecutor") Executor apiExecutor)
    {
        ticketCommentService = _ticketCommentService;
        this.apiExecutor = apiExecutor;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<TicketComment>>> getAllTicketComments()
    {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(ticketCommentService.getAllTicketComment()), apiExecutor);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<TicketComment>> getTicketCommentById(@PathVariable Long id)
    {
        return CompletableFuture.supplyAsync(() -> ticketCommentService.getTicketCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()), apiExecutor);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<TicketComment>> createTicketComment(@RequestBody TicketComment comment)
    {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(ticketCommentService.createTicketComment(comment)), apiExecutor);
    }
}
