package com.yigitaslan.call_center_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "support_tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    // Yeni eklenen müşteri ilişkisi
    @Column(name = "customer_id")
    private Long customerId;

    // Yeni eklenen temsilci ilişkisi
    @Column(name = "agent_id")
    private Long agentId;

    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    private Boolean status;

    private String mars;

    private Boolean isactive;

    @Column(name = "createdate", updatable = false)
    private LocalDateTime createdate;

    @Column(name = "updateddate")
    private LocalDateTime updateddate;
}
