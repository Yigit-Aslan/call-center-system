package com.yigitaslan.call_center_system.controller;


import com.yigitaslan.call_center_system.model.Customer;
import com.yigitaslan.call_center_system.service.ICustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final ICustomerService customerService;
    private final Executor apiExecutor;

    public CustomerController(ICustomerService _customerService, @Qualifier("apiExecutor") Executor apiExecutor){
        customerService = _customerService;
        this.apiExecutor = apiExecutor;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Customer>>> getAllCustomers(){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(customerService.getAllCustomers()), apiExecutor);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Customer>> getCustomerById(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()), apiExecutor);
    }

    // 3. Yeni talep oluştur (POST: /api/customers)
    @PostMapping
    public CompletableFuture<ResponseEntity<Customer>> createCustomer(@RequestBody Customer customer){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(customerService.createCustomer(customer)), apiExecutor);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Customer>> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(customerService.updateCustomer(id, customer)), apiExecutor);
    }

    // 4. Talep sil (DELETE: /api/tickets/{id})
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteCustomer(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok().build();
        }, apiExecutor);
    }
}
