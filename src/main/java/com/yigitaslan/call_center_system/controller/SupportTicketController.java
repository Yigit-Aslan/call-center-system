package com.yigitaslan.call_center_system.controller;

import com.yigitaslan.call_center_system.model.SupportTicket;
import com.yigitaslan.call_center_system.service.ISupportTicketService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api/tickets")
public class SupportTicketController {
    private final ISupportTicketService ticketService;
    private final Executor apiExecutor;

    public SupportTicketController(ISupportTicketService _ticketService, @Qualifier("apiExecutor") Executor apiExecutor){
        ticketService = _ticketService;
        this.apiExecutor = apiExecutor;
    }
    // 1. Tüm talepleri listele (GET: /api/tickets)
    @GetMapping
    public CompletableFuture<ResponseEntity<List<SupportTicket>>> getAllTickets(){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(ticketService.getAllTickets()), apiExecutor);
    }
    // 2. ID'ye göre talep getir (GET: /api/tickets/{id})
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<SupportTicket>> getTicketById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ticketService.getTicketById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()), apiExecutor);
    }
    // 3. Yeni talep oluştur (POST: /api/tickets)
    @PostMapping
    public CompletableFuture<ResponseEntity<SupportTicket>> createTicket(@RequestBody SupportTicket ticket){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(ticketService.createTicket(ticket)), apiExecutor);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<SupportTicket>> updateTicket(@PathVariable Long id, @RequestBody SupportTicket ticket) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(ticketService.updateTicket(id, ticket)), apiExecutor);
    }
        // 4. Talep sil (DELETE: /api/tickets/{id})
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteTicket(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            ticketService.deleteTicket(id);
            return ResponseEntity.ok().build();
        }, apiExecutor);
    }
}
