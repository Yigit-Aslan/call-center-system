package com.yigitaslan.call_center_system.service.impl;

import com.yigitaslan.call_center_system.model.Agent;
import com.yigitaslan.call_center_system.model.Customer;
import com.yigitaslan.call_center_system.repository.IAgentRepository;
import com.yigitaslan.call_center_system.service.IAgentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements IAgentService {
    private final IAgentRepository agentRepository;

    public AgentServiceImpl(IAgentRepository _agentRepository){
        agentRepository = _agentRepository;
    }

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public Optional<Agent> getAgentById(Long id) {
        return agentRepository.findById(id);
    }

    @Override
    public Agent createAgent(Agent agent) {

        LocalDateTime now = LocalDateTime.now();
        agent.setCreateDate(now);

        if (agent.getIsactive() == null) {
            agent.setIsactive(true);
        }

        return agentRepository.save(agent);
    }

    @Override
    public Agent updateAgent(Long id, Agent agentDetails) {

        Agent existingAgent = agentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agent bulunamadı"));


        existingAgent.setFirstName(agentDetails.getFirstName());
        existingAgent.setLastName(agentDetails.getLastName());
        existingAgent.setUsername(agentDetails.getUsername());
        existingAgent.setEmail(agentDetails.getEmail());
        existingAgent.setUpdatedDate(LocalDateTime.now());
        if (agentDetails.getIsactive() != null) {
            existingAgent.setIsactive(agentDetails.getIsactive());
        }

        return agentRepository.save(existingAgent);
    }

    @Override
    public void deleteAgent(Long id) {
        Agent existingAgent = agentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agent bulunamadı"));

        existingAgent.setIsactive(false);

        existingAgent.setUpdatedDate(LocalDateTime.now());

        agentRepository.save(existingAgent);
    }

}
