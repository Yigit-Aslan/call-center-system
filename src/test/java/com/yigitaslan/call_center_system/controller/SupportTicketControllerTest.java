package com.yigitaslan.call_center_system.controller;

import com.yigitaslan.call_center_system.model.SupportTicket;
import com.yigitaslan.call_center_system.service.ISupportTicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SupportTicketControllerTest {
    @Mock
    private ISupportTicketService ticketService;

    @InjectMocks
    private SupportTicketController ticketController;

    @Test
    void getAllTickets_ShouldReturnOkWithTickets() {
        List<SupportTicket> tickets = List.of(createTicket(1L));
        when(ticketService.getAllTickets()).thenReturn(tickets);

        var response = ticketController.getAllTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
        verify(ticketService).getAllTickets();
    }

    @Test
    void getTicketById_WhenTicketExists_ShouldReturnOkWithTicket() {
        SupportTicket ticket = createTicket(1L);
        when(ticketService.getTicketById(1L)).thenReturn(Optional.of(ticket));

        var response = ticketController.getTicketById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
        verify(ticketService).getTicketById(1L);
    }

    @Test
    void getTicketById_WhenTicketDoesNotExist_ShouldReturnNotFound() {
        when(ticketService.getTicketById(99L)).thenReturn(Optional.empty());

        var response = ticketController.getTicketById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(ticketService).getTicketById(99L);
    }

    @Test
    void createTicket_ShouldReturnOkWithCreatedTicket() {
        SupportTicket ticket = createTicket(null);
        when(ticketService.createTicket(ticket)).thenReturn(ticket);

        var response = ticketController.createTicket(ticket);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
        verify(ticketService).createTicket(ticket);
    }

    @Test
    void updateTicket_ShouldReturnOkWithUpdatedTicket() {
        SupportTicket ticket = createTicket(1L);
        when(ticketService.updateTicket(1L, ticket)).thenReturn(ticket);

        var response = ticketController.updateTicket(1L, ticket);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
        verify(ticketService).updateTicket(1L, ticket);
    }

    @Test
    void deleteTicket_ShouldReturnOkAndDelegateDeletion() {
        var response = ticketController.deleteTicket(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(ticketService).deleteTicket(1L);
    }

    private SupportTicket createTicket(Long id) {
        SupportTicket ticket = new SupportTicket();
        ticket.setId(id);
        ticket.setCustomerId(2L);
        ticket.setAgentId(3L);
        ticket.setCategoryId(4L);
        ticket.setTitle("Test ticket");
        ticket.setDescription("Test description");
        return ticket;
    }
}
