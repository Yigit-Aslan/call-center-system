package com.yigitaslan.call_center_system.service.impl;

import com.yigitaslan.call_center_system.model.Agent;
import com.yigitaslan.call_center_system.repository.IAgentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgentServiceImplTest {
    @Mock
    private IAgentRepository agentRepository;

    @InjectMocks
    private AgentServiceImpl agentService;

    @Test
    void getAllAgents_ShouldReturnAllAgents() {
        List<Agent> agents = List.of(createAgent(1L), createAgent(2L));
        when(agentRepository.findAll()).thenReturn(agents);

        List<Agent> result = agentService.getAllAgents();

        assertEquals(agents, result);
        verify(agentRepository).findAll();
    }

    @Test
    void getAgentById_WhenAgentExists_ShouldReturnAgent() {
        Agent agent = createAgent(1L);
        when(agentRepository.findById(1L)).thenReturn(Optional.of(agent));

        Optional<Agent> result = agentService.getAgentById(1L);

        assertTrue(result.isPresent());
        assertEquals(agent, result.get());
        verify(agentRepository).findById(1L);
    }

    @Test
    void getAgentById_WhenAgentDoesNotExist_ShouldReturnEmpty() {
        when(agentRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Agent> result = agentService.getAgentById(99L);

        assertTrue(result.isEmpty());
        verify(agentRepository).findById(99L);
    }

    @Test
    void createAgent_WhenActiveStatusIsUnset_ShouldSetDefaultsAndSave() {
        Agent agent = new Agent();
        agent.setFirstName("Ada");
        agent.setLastName("Lovelace");
        agent.setUsername("ada");
        agent.setEmail("ada@example.com");
        when(agentRepository.save(agent)).thenReturn(agent);

        LocalDateTime beforeCreate = LocalDateTime.now();
        Agent result = agentService.createAgent(agent);
        LocalDateTime afterCreate = LocalDateTime.now();

        assertSame(agent, result);
        assertTrue(result.getIsactive());
        assertNotNull(result.getCreateDate());
        assertFalse(result.getCreateDate().isBefore(beforeCreate));
        assertFalse(result.getCreateDate().isAfter(afterCreate));
        verify(agentRepository).save(agent);
    }

    @Test
    void createAgent_WhenActiveStatusIsSet_ShouldPreserveIt() {
        Agent agent = createAgent(1L);
        agent.setIsactive(false);
        when(agentRepository.save(agent)).thenReturn(agent);

        Agent result = agentService.createAgent(agent);

        assertFalse(result.getIsactive());
        assertNotNull(result.getCreateDate());
        verify(agentRepository).save(agent);
    }

    @Test
    void updateAgent_WhenAgentExists_ShouldUpdateAndSave() {
        Long agentId = 1L;
        Agent existingAgent = createAgent(agentId);
        existingAgent.setFirstName("Old");
        existingAgent.setIsactive(true);

        Agent updateDetails = createAgent(null);
        updateDetails.setFirstName("Ada");
        updateDetails.setLastName("Lovelace");
        updateDetails.setUsername("ada");
        updateDetails.setEmail("ada@example.com");
        updateDetails.setIsactive(false);

        when(agentRepository.findById(agentId)).thenReturn(Optional.of(existingAgent));
        when(agentRepository.save(existingAgent)).thenReturn(existingAgent);
        LocalDateTime beforeUpdate = LocalDateTime.now();

        Agent result = agentService.updateAgent(agentId, updateDetails);

        LocalDateTime afterUpdate = LocalDateTime.now();
        assertSame(existingAgent, result);
        assertEquals(agentId, result.getId());
        assertEquals("Ada", result.getFirstName());
        assertEquals("Lovelace", result.getLastName());
        assertEquals("ada", result.getUsername());
        assertEquals("ada@example.com", result.getEmail());
        assertFalse(result.getIsactive());
        assertFalse(result.getUpdatedDate().isBefore(beforeUpdate));
        assertFalse(result.getUpdatedDate().isAfter(afterUpdate));
        verify(agentRepository).findById(agentId);
        verify(agentRepository).save(existingAgent);
    }

    @Test
    void updateAgent_WhenActiveStatusIsUnset_ShouldPreserveExistingStatus() {
        Long agentId = 1L;
        Agent existingAgent = createAgent(agentId);
        existingAgent.setIsactive(true);
        Agent updateDetails = createAgent(null);
        updateDetails.setIsactive(null);

        when(agentRepository.findById(agentId)).thenReturn(Optional.of(existingAgent));
        when(agentRepository.save(existingAgent)).thenReturn(existingAgent);

        Agent result = agentService.updateAgent(agentId, updateDetails);

        assertTrue(result.getIsactive());
        verify(agentRepository).save(existingAgent);
    }

    @Test
    void updateAgent_WhenAgentDoesNotExist_ShouldThrowExceptionWithoutSaving() {
        Long agentId = 99L;
        when(agentRepository.findById(agentId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> agentService.updateAgent(agentId, new Agent()));

        assertEquals("Agent bulunamadı", exception.getMessage());
        verify(agentRepository).findById(agentId);
        verify(agentRepository, never()).save(any(Agent.class));
    }

    @Test
    void deleteAgent_WhenAgentExists_ShouldDeactivateAndSave() {
        Long agentId = 1L;
        Agent agent = createAgent(agentId);
        agent.setIsactive(true);
        when(agentRepository.findById(agentId)).thenReturn(Optional.of(agent));
        LocalDateTime beforeDelete = LocalDateTime.now();

        agentService.deleteAgent(agentId);

        LocalDateTime afterDelete = LocalDateTime.now();
        assertFalse(agent.getIsactive());
        assertFalse(agent.getUpdatedDate().isBefore(beforeDelete));
        assertFalse(agent.getUpdatedDate().isAfter(afterDelete));
        verify(agentRepository).findById(agentId);
        verify(agentRepository).save(agent);
    }

    @Test
    void deleteAgent_WhenAgentDoesNotExist_ShouldThrowExceptionWithoutSaving() {
        Long agentId = 99L;
        when(agentRepository.findById(agentId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> agentService.deleteAgent(agentId));

        assertEquals("Agent bulunamadı", exception.getMessage());
        verify(agentRepository).findById(agentId);
        verify(agentRepository, never()).save(any(Agent.class));
    }

    private Agent createAgent(Long id) {
        Agent agent = new Agent();
        agent.setId(id);
        agent.setFirstName("Agent");
        agent.setLastName("User");
        agent.setUsername("agent" + id);
        agent.setEmail("agent" + id + "@example.com");
        agent.setIsactive(true);
        return agent;
    }
}
