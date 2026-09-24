package com.yigitaslan.call_center_system.service;

import com.yigitaslan.call_center_system.model.SupportTicket;
import java.util.List;
import java.util.Optional;

public interface ISupportTicketService {
    List<SupportTicket> getAllTickets();
    Optional<SupportTicket> getTicketById(Long id);
    SupportTicket createTicket(SupportTicket ticket);
    SupportTicket updateTicket(Long id, SupportTicket ticket);
    void deleteTicket(Long id);
}
