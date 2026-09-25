package com.yigitaslan.call_center_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_ticket_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hangi ticket'a ait olduğunu tuttuğumuz alan
    @Column(name = "ticket_id", nullable = false)
    private Long ticketId;

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String comment;

    @Column(name = "createdate")
    private LocalDateTime createDate = LocalDateTime.now();
}