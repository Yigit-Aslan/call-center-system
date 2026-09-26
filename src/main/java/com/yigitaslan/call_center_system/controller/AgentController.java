package com.yigitaslan.call_center_system.controller;

import com.yigitaslan.call_center_system.model.Agent;
import com.yigitaslan.call_center_system.service.IAgentService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/agent")
public class AgentController {
    private final IAgentService agentService;

    public AgentController(IAgentService _agentService){
        agentService = _agentService;
    }

    @GetMapping
    public ResponseEntity<List<Agent>> getAllAgents(){
        return ResponseEntity.ok(agentService.getAllAgents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agent> getAgentById(@PathVariable Long id)
    {
        return agentService.getAgentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent)
    {
        Agent createAgent = agentService.createAgent(agent);

        return ResponseEntity.ok(createAgent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agent> updatedAgent(@PathVariable Long id, @RequestBody Agent agent)
    {
        Agent updatedAgent = agentService.updateAgent(id, agent);

        return ResponseEntity.ok(updatedAgent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Agent> deleteAgent(@PathVariable Long id)
    {
        agentService.deleteAgent(id);

        return ResponseEntity.ok().build();
    }
}
