package com.yigitaslan.call_center_system.controller;

import com.yigitaslan.call_center_system.model.Agent;
import com.yigitaslan.call_center_system.service.IAgentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgentControllerTest {
    @Mock
    private IAgentService agentService;

    @InjectMocks
    private AgentController agentController;

    @Test
    void getAllAgents_ShouldReturnOkWithAgents() {
        List<Agent> agents = List.of(createAgent(1L));
        when(agentService.getAllAgents()).thenReturn(agents);

        var response = agentController.getAllAgents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agents, response.getBody());
        verify(agentService).getAllAgents();
    }

    @Test
    void getAgentById_WhenAgentExists_ShouldReturnOkWithAgent() {
        Agent agent = createAgent(1L);
        when(agentService.getAgentById(1L)).thenReturn(Optional.of(agent));

        var response = agentController.getAgentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agent, response.getBody());
        verify(agentService).getAgentById(1L);
    }

    @Test
    void getAgentById_WhenAgentDoesNotExist_ShouldReturnNotFound() {
        when(agentService.getAgentById(99L)).thenReturn(Optional.empty());

        var response = agentController.getAgentById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(agentService).getAgentById(99L);
    }

    @Test
    void createAgent_ShouldReturnOkWithCreatedAgent() {
        Agent agent = createAgent(null);
        when(agentService.createAgent(agent)).thenReturn(agent);

        var response = agentController.createAgent(agent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agent, response.getBody());
        verify(agentService).createAgent(agent);
    }

    @Test
    void updateAgent_ShouldReturnOkWithUpdatedAgent() {
        Agent agent = createAgent(1L);
        when(agentService.updateAgent(1L, agent)).thenReturn(agent);

        var response = agentController.updatedAgent(1L, agent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agent, response.getBody());
        verify(agentService).updateAgent(1L, agent);
    }

    @Test
    void deleteAgent_ShouldReturnOkAndDelegateDeletion() {
        var response = agentController.deleteAgent(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(agentService).deleteAgent(1L);
    }

    private Agent createAgent(Long id) {
        Agent agent = new Agent();
        agent.setId(id);
        agent.setFirstName("Ada");
        agent.setLastName("Lovelace");
        agent.setUsername("ada");
        agent.setEmail("ada@example.com");
        return agent;
    }
}
