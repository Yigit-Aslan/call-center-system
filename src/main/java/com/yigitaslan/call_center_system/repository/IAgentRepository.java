package com.yigitaslan.call_center_system.repository;

import com.yigitaslan.call_center_system.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAgentRepository extends JpaRepository<Agent, Long>{
}
