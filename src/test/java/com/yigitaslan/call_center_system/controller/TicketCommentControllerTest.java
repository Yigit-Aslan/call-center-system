package com.yigitaslan.call_center_system.controller;

import com.yigitaslan.call_center_system.model.TicketComment;
import com.yigitaslan.call_center_system.service.ITicketCommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketCommentControllerTest {
    @Mock
    private ITicketCommentService ticketCommentService;

    @InjectMocks
    private TicketCommentController ticketCommentController;

    @Test
    void getAllTicketComments_ShouldReturnOkWithComments() {
        List<TicketComment> comments = List.of(createComment(1L));
        when(ticketCommentService.getAllTicketComment()).thenReturn(comments);

        var response = ticketCommentController.getAllTicketComments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comments, response.getBody());
        verify(ticketCommentService).getAllTicketComment();
    }

    @Test
    void getTicketCommentById_WhenCommentExists_ShouldReturnOkWithComment() {
        TicketComment comment = createComment(1L);
        when(ticketCommentService.getTicketCommentById(1L)).thenReturn(Optional.of(comment));

        var response = ticketCommentController.getTicketCommentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comment, response.getBody());
        verify(ticketCommentService).getTicketCommentById(1L);
    }

    @Test
    void getTicketCommentById_WhenCommentDoesNotExist_ShouldReturnNotFound() {
        when(ticketCommentService.getTicketCommentById(99L)).thenReturn(Optional.empty());

        var response = ticketCommentController.getTicketCommentById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(ticketCommentService).getTicketCommentById(99L);
    }

    @Test
    void createTicketComment_ShouldReturnOkWithCreatedComment() {
        TicketComment comment = createComment(null);
        when(ticketCommentService.createTicketComment(comment)).thenReturn(comment);

        var response = ticketCommentController.createTicketComment(comment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comment, response.getBody());
        verify(ticketCommentService).createTicketComment(comment);
    }

    @Test
    void createTicketComment_ShouldBindCommentFromRequestBody() throws Exception {
        Method method = TicketCommentController.class.getMethod("createTicketComment", TicketComment.class);

        assertNotNull(method.getParameters()[0].getAnnotation(RequestBody.class));
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
