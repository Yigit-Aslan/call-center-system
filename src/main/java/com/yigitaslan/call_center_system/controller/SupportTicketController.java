package com.yigitaslan.call_center_system.controller;

import com.yigitaslan.call_center_system.model.SupportTicket;
import com.yigitaslan.call_center_system.service.ISupportTicketService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/tickets")
public class SupportTicketController {
    private final ISupportTicketService ticketService;

    public SupportTicketController(ISupportTicketService _ticketService){
        ticketService = _ticketService;
    }
    // 1. Tüm talepleri listele (GET: /api/tickets)
    @GetMapping
    public ResponseEntity<List<SupportTicket>> getAllTickets(){
        return ResponseEntity.ok(ticketService.getAllTickets());
    }
    // 2. ID'ye göre talep getir (GET: /api/tickets/{id})
    @GetMapping("/{id}")
    public ResponseEntity<SupportTicket> getTicketById(@PathVariable Long id) {
        return  ticketService.getTicketById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // 3. Yeni talep oluştur (POST: /api/tickets)
    @PostMapping
    public ResponseEntity<SupportTicket> createTicket(@RequestBody SupportTicket ticket){
        SupportTicket createdTicket = ticketService.createTicket(ticket);
        return ResponseEntity.ok(createdTicket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportTicket> updateTicket(@PathVariable Long id, @RequestBody SupportTicket ticket) {
        SupportTicket updatedTicket = ticketService.updateTicket(id, ticket);
        return ResponseEntity.ok(updatedTicket);
    }
        // 4. Talep sil (DELETE: /api/tickets/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }
}
