package com.yigitaslan.call_center_system.service.impl;

import com.yigitaslan.call_center_system.model.TicketComment;
import com.yigitaslan.call_center_system.repository.ITicketCommentRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketCommentServiceImplTest {
    @Mock
    private ITicketCommentRepository ticketCommentRepository;

    @InjectMocks
    private TicketCommentServiceImpl ticketCommentService;

    @Test
    void getAllTicketComment_ShouldReturnAllComments() {
        List<TicketComment> comments = List.of(createComment(1L), createComment(2L));
        when(ticketCommentRepository.findAll()).thenReturn(comments);

        List<TicketComment> result = ticketCommentService.getAllTicketComment();

        assertEquals(comments, result);
        verify(ticketCommentRepository).findAll();
    }

    @Test
    void getTicketCommentById_WhenCommentExists_ShouldReturnComment() {
        TicketComment comment = createComment(1L);
        when(ticketCommentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Optional<TicketComment> result = ticketCommentService.getTicketCommentById(1L);

        assertTrue(result.isPresent());
        assertEquals(comment, result.get());
        verify(ticketCommentRepository).findById(1L);
    }

    @Test
    void getTicketCommentById_WhenCommentDoesNotExist_ShouldReturnEmpty() {
        when(ticketCommentRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<TicketComment> result = ticketCommentService.getTicketCommentById(99L);

        assertTrue(result.isEmpty());
        verify(ticketCommentRepository).findById(99L);
    }

    @Test
    void createTicketComment_ShouldSetCreationDateAndSave() {
        TicketComment comment = createComment(null);
        when(ticketCommentRepository.save(comment)).thenReturn(comment);
        LocalDateTime beforeCreate = LocalDateTime.now();

        TicketComment result = ticketCommentService.createTicketComment(comment);

        LocalDateTime afterCreate = LocalDateTime.now();
        assertSame(comment, result);
        assertNotNull(result.getCreateDate());
        assertFalse(result.getCreateDate().isBefore(beforeCreate));
        assertFalse(result.getCreateDate().isAfter(afterCreate));
        verify(ticketCommentRepository).save(comment);
    }

    private TicketComment createComment(Long id) {
        TicketComment comment = new TicketComment();
        comment.setId(id);
        comment.setTicketId(10L);
        comment.setAgentId(20L);
        comment.setComment("Ticket update");
        return comment;
    }
}
