package com.yigitaslan.call_center_system.repository;

import com.yigitaslan.call_center_system.model.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISupportTicketRepository extends JpaRepository<SupportTicket, Integer> {
}
