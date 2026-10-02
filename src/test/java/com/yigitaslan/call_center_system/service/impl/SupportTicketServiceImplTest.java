package com.yigitaslan.call_center_system.service.impl;

import com.yigitaslan.call_center_system.model.SupportTicket;
import com.yigitaslan.call_center_system.repository.ISupportTicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupportTicketServiceImplTest {
    @Mock
    private ISupportTicketRepository supportTicketRepository;

    @InjectMocks
    private SupportTicketServiceImpl supportTicketService;

    @Test
    void getAllTickets_ShouldReturnAllTickets() {
        List<SupportTicket> tickets = List.of(createTicket(1L), createTicket(2L));
        when(supportTicketRepository.findAll()).thenReturn(tickets);

        List<SupportTicket> result = supportTicketService.getAllTickets();

        assertEquals(tickets, result);
        verify(supportTicketRepository).findAll();
    }

    @Test
    void getTicketById_WhenTicketExists_ShouldReturnTicket() {
        SupportTicket ticket = createTicket(1L);
        when(supportTicketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        Optional<SupportTicket> result = supportTicketService.getTicketById(1L);

        assertTrue(result.isPresent());
        assertEquals(ticket, result.get());
        verify(supportTicketRepository).findById(1L);
    }

    @Test
    void getTicketById_WhenTicketDoesNotExist_ShouldReturnEmpty() {
        when(supportTicketRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<SupportTicket> result = supportTicketService.getTicketById(99L);

        assertTrue(result.isEmpty());
        verify(supportTicketRepository).findById(99L);
    }

    @Test
    void createTicket_WhenActiveStatusIsUnset_ShouldSetDefaultsAndSave() {
        SupportTicket ticket = createTicket(null);
        ticket.setIsactive(null);
        when(supportTicketRepository.save(ticket)).thenReturn(ticket);
        LocalDateTime beforeCreate = LocalDateTime.now();

        SupportTicket result = supportTicketService.createTicket(ticket);

        LocalDateTime afterCreate = LocalDateTime.now();
        assertSame(ticket, result);
        assertTrue(result.getIsactive());
        assertNotNull(result.getCreatedate());
        assertFalse(result.getCreatedate().isBefore(beforeCreate));
        assertFalse(result.getCreatedate().isAfter(afterCreate));
        verify(supportTicketRepository).save(ticket);
    }

    @Test
    void createTicket_WhenActiveStatusIsSet_ShouldPreserveIt() {
        SupportTicket ticket = createTicket(null);
        ticket.setIsactive(false);
        when(supportTicketRepository.save(ticket)).thenReturn(ticket);

        SupportTicket result = supportTicketService.createTicket(ticket);

        assertFalse(result.getIsactive());
        assertNotNull(result.getCreatedate());
        verify(supportTicketRepository).save(ticket);
    }

    @Test
    void updateTicket_WhenTicketExists_ShouldUpdateAndSave() {
        Long ticketId = 1L;
        SupportTicket existingTicket = createTicket(ticketId);
        SupportTicket updateDetails = new SupportTicket();
        updateDetails.setCategoryId(5L);
        updateDetails.setTitle("Updated title");
        updateDetails.setDescription("Updated description");
        updateDetails.setStatus(false);
        updateDetails.setMars("Updated priority");
        updateDetails.setIsactive(false);

        when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));
        when(supportTicketRepository.save(existingTicket)).thenReturn(existingTicket);
        LocalDateTime beforeUpdate = LocalDateTime.now();

        SupportTicket result = supportTicketService.updateTicket(ticketId, updateDetails);

        LocalDateTime afterUpdate = LocalDateTime.now();
        assertSame(existingTicket, result);
        assertEquals(ticketId, result.getId());
        assertEquals(5L, result.getCategoryId());
        assertEquals("Updated title", result.getTitle());
        assertEquals("Updated description", result.getDescription());
        assertFalse(result.getStatus());
        assertEquals("Updated priority", result.getMars());
        assertFalse(result.getIsactive());
        assertNotNull(result.getUpdateddate());
        assertFalse(result.getUpdateddate().isBefore(beforeUpdate));
        assertFalse(result.getUpdateddate().isAfter(afterUpdate));
        verify(supportTicketRepository).findById(ticketId);
        verify(supportTicketRepository).save(existingTicket);
    }

    @Test
    void updateTicket_WhenTicketDoesNotExist_ShouldThrowExceptionWithoutSaving() {
        Long ticketId = 99L;
        when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> supportTicketService.updateTicket(ticketId, new SupportTicket()));

        assertEquals("Talep bulunamadı", exception.getMessage());
        verify(supportTicketRepository).findById(ticketId);
        verify(supportTicketRepository, never()).save(any(SupportTicket.class));
    }

    @Test
    void deleteTicket_WhenTicketExists_ShouldDeactivateAndSave() {
        Long ticketId = 1L;
        SupportTicket ticket = createTicket(ticketId);
        ticket.setIsactive(true);
        when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        LocalDateTime beforeDelete = LocalDateTime.now();

        supportTicketService.deleteTicket(ticketId);

        LocalDateTime afterDelete = LocalDateTime.now();
        assertFalse(ticket.getIsactive());
        assertNotNull(ticket.getUpdateddate());
        assertFalse(ticket.getUpdateddate().isBefore(beforeDelete));
        assertFalse(ticket.getUpdateddate().isAfter(afterDelete));
        verify(supportTicketRepository).findById(ticketId);
        verify(supportTicketRepository).save(ticket);
    }

    @Test
    void deleteTicket_WhenTicketDoesNotExist_ShouldThrowExceptionWithoutSaving() {
        Long ticketId = 99L;
        when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> supportTicketService.deleteTicket(ticketId));

        assertEquals("Talep bulunamadı", exception.getMessage());
        verify(supportTicketRepository).findById(ticketId);
        verify(supportTicketRepository, never()).save(any(SupportTicket.class));
    }

    private SupportTicket createTicket(Long id) {
        SupportTicket ticket = new SupportTicket();
        ticket.setId(id);
        ticket.setCategoryId(2L);
        ticket.setCustomerId(3L);
        ticket.setAgentId(4L);
        ticket.setTitle("Initial title");
        ticket.setDescription("Initial description");
        ticket.setStatus(true);
        ticket.setMars("Normal");
        ticket.setIsactive(true);
        return ticket;
    }
}
