package com.yigitaslan.call_center_system.controller;

import com.yigitaslan.call_center_system.model.Customer;
import com.yigitaslan.call_center_system.service.ICustomerService;
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
class CustomerControllerTest {
    @Mock
    private ICustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void getAllCustomers_ShouldReturnOkWithCustomers() {
        List<Customer> customers = List.of(createCustomer(1L));
        when(customerService.getAllCustomers()).thenReturn(customers);

        var response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
        verify(customerService).getAllCustomers();
    }

    @Test
    void getCustomerById_WhenCustomerExists_ShouldReturnOkWithCustomer() {
        Customer customer = createCustomer(1L);
        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));

        var response = customerController.getCustomerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
        verify(customerService).getCustomerById(1L);
    }

    @Test
    void getCustomerById_WhenCustomerDoesNotExist_ShouldReturnNotFound() {
        when(customerService.getCustomerById(99L)).thenReturn(Optional.empty());

        var response = customerController.getCustomerById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(customerService).getCustomerById(99L);
    }

    @Test
    void createCustomer_ShouldReturnOkWithCreatedCustomer() {
        Customer customer = createCustomer(null);
        when(customerService.createCustomer(customer)).thenReturn(customer);

        var response = customerController.createCustomer(customer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
        verify(customerService).createCustomer(customer);
    }

    @Test
    void updateCustomer_ShouldReturnOkWithUpdatedCustomer() {
        Customer customer = createCustomer(1L);
        when(customerService.updateCustomer(1L, customer)).thenReturn(customer);

        var response = customerController.updateCustomer(1L, customer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
        verify(customerService).updateCustomer(1L, customer);
    }

    @Test
    void deleteCustomer_ShouldReturnOkAndDelegateDeletion() {
        var response = customerController.deleteCustomer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerService).deleteCustomer(1L);
    }

    private Customer createCustomer(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName("Ada");
        customer.setLastName("Lovelace");
        customer.setEmail("ada@example.com");
        return customer;
    }
}
