package com.yigitaslan.call_center_system.service;

import com.yigitaslan.call_center_system.model.TicketComment;
import java.util.List;
import java.util.Optional;

public interface ITicketCommentService {
    List<TicketComment> getAllTicketComment();
    Optional<TicketComment> getTicketCommentById(Long id);
    TicketComment createTicketComment(TicketComment comment);
}
