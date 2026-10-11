package com.yigitaslan.call_center_system.controller;

import com.yigitaslan.call_center_system.model.Agent;
import com.yigitaslan.call_center_system.service.IAgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api/agent")
public class AgentController {
    private final IAgentService agentService;
    private final Executor apiExecutor;

    public AgentController(IAgentService _agentService, @Qualifier("apiExecutor") Executor apiExecutor){
        agentService = _agentService;
        this.apiExecutor = apiExecutor;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Agent>>> getAllAgents(){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(agentService.getAllAgents()), apiExecutor);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Agent>> getAgentById(@PathVariable Long id)
    {
        return CompletableFuture.supplyAsync(() -> agentService.getAgentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()), apiExecutor);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Agent>> createAgent(@RequestBody Agent agent)
    {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(agentService.createAgent(agent)), apiExecutor);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Agent>> updatedAgent(@PathVariable Long id, @RequestBody Agent agent)
    {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(agentService.updateAgent(id, agent)), apiExecutor);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteAgent(@PathVariable Long id)
    {
        return CompletableFuture.supplyAsync(() -> {
            agentService.deleteAgent(id);
            return ResponseEntity.ok().build();
        }, apiExecutor);
    }
}
