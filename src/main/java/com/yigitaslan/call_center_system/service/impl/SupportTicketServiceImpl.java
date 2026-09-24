package com.yigitaslan.call_center_system.service.impl;

import com.yigitaslan.call_center_system.model.SupportTicket;
import com.yigitaslan.call_center_system.repository.ISupportTicketRepository;
import com.yigitaslan.call_center_system.service.ISupportTicketService;
import org.springframework.stereotype.Service;

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
    public Optional<SupportTicket> getTicketById(int id) {
        return supportTicketRepository.findById(id);
    }

    @Override
    public SupportTicket createTicket(SupportTicket ticket) {
        return supportTicketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(int id) {
        supportTicketRepository.deleteById(id);
    }
}