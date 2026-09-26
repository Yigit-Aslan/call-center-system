package com.yigitaslan.call_center_system.service.impl;

import com.yigitaslan.call_center_system.model.Customer;
import com.yigitaslan.call_center_system.model.SupportTicket;
import com.yigitaslan.call_center_system.repository.ICustomerRepository;
import com.yigitaslan.call_center_system.service.ICustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements  ICustomerService{
    private final ICustomerRepository customerRepository;

    public CustomerServiceImpl(ICustomerRepository _customerRepository){
        customerRepository = _customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {

        LocalDateTime now = LocalDateTime.now();
        customer.setCreateDate(now);

        if (customer.getIsactive() == null) {
            customer.setIsactive(true);
        }

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customerDetails) {

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));


        existingCustomer.setFirstName(customerDetails.getFirstName());
        existingCustomer.setLastName(customerDetails.getLastName());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setPhone(customerDetails.getPhone());
        existingCustomer.setTcNo(customerDetails.getTcNo());

        if (customerDetails.getIsactive() != null) {
            existingCustomer.setIsactive(customerDetails.getIsactive());
        }

        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));

        existingCustomer.setIsactive(false);

        existingCustomer.setUpdatedDate(LocalDateTime.now());

        customerRepository.save(existingCustomer);
    }
}
