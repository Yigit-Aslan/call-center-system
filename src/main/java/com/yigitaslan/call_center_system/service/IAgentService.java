package com.yigitaslan.call_center_system.service;

import com.yigitaslan.call_center_system.model.Agent;

import java.util.List;
import java.util.Optional;

public interface IAgentService {
    List<Agent> getAllAgents();
    Optional<Agent> getAgentById(Long id);
    Agent createAgent(Agent agent);
    Agent updateAgent(Long id, Agent agent);
    void deleteAgent(Long id);
}
