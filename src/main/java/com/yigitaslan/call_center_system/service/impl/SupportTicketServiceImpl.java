package com.yigitaslan.call_center_system.service.impl;

import com.yigitaslan.call_center_system.model.SupportTicket;
import com.yigitaslan.call_center_system.repository.ISupportTicketRepository;
import com.yigitaslan.call_center_system.service.ISupportTicketService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SupportTicketServiceImpl implements ISupportTicketService {
    private final ISupportTicketRepository supportTicketRepository;

    // Constructor Injection
    public SupportTicketServiceImpl(ISupportTicketRepository _supportTicketRepository) {
        supportTicketRepository = _supportTicketRepository;
    }

    @Override
    public List<SupportTicket> getAllTickets() {
        return supportTicketRepository.findAll();
    }

    @Override
    public Optional<SupportTicket> getTicketById(Long id) {
        return supportTicketRepository.findById(id);
    }

    @Override
    public SupportTicket createTicket(SupportTicket ticket) {

        LocalDateTime now = LocalDateTime.now();
        ticket.setCreatedate(now);

        if (ticket.getIsactive() == null) {
            ticket.setIsactive(true);
        }

        return supportTicketRepository.save(ticket);
    }

    @Override
    public SupportTicket updateTicket(Long id, SupportTicket ticketDetails) {

        SupportTicket existingTicket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talep bulunamadı"));

        existingTicket.setTitle(ticketDetails.getTitle());
        existingTicket.setDescription(ticketDetails.getDescription());
        existingTicket.setStatus(ticketDetails.getStatus());
        existingTicket.setMars(ticketDetails.getMars());
        existingTicket.setIsactive(ticketDetails.getIsactive());
        existingTicket.setCategoryId(ticketDetails.getCategoryId());
        existingTicket.setUpdateddate(LocalDateTime.now());

        return supportTicketRepository.save(existingTicket);
    }

    @Override
    public void deleteTicket(Long id) {
        SupportTicket existingTicket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talep bulunamadı"));

        existingTicket.setIsactive(false);

        existingTicket.setUpdateddate(LocalDateTime.now());

        supportTicketRepository.save(existingTicket);
    }
}