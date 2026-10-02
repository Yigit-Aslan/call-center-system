package com.yigitaslan.call_center_system.service.impl;

import com.yigitaslan.call_center_system.model.Customer;
import com.yigitaslan.call_center_system.repository.ICustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private ICustomerRepository customerRepository; // Gerçek veritabanına gitmeyen sahte (mock) repo

    @InjectMocks
    private CustomerServiceImpl customerService; // Test edeceğimiz gerçek servis sınıfı


    @Test
    void getCustomerById_WhenCustomerExists_ShouldReturnCustomer() {
        // 1. Arrange (Hazırlık): Senaryo için sahte veri ve davranış tanımlama
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1L);
        mockCustomer.setFirstName("Yiğit");
        mockCustomer.setLastName("Aslan");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));

        // 2. Act (Çalıştırma): Test edilen metodu tetikleme
        Optional<Customer> result = customerService.getCustomerById(1L);

        // 3. Assert (Doğrulama): Sonuçların beklenenle uyuşup uyuşmadığını kontrol etme
        assertNotNull(result);
        assertEquals("Yiğit", result.get().getFirstName());
        assertEquals("Aslan", result.get().getLastName());

        // Repository'nin findById metodunun tam 1 kez çağrıldığını doğrula
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void getCustomerById_WhenCustomerDoesNotExist_ShouldReturnEmpty() {
        // 1. Arrange: Müşteri bulunamadığında Optional.empty döneceğini simüle ediyoruz
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        // 2. Act
        Optional<Customer> result = customerService.getCustomerById(99L);

        // 3. Assert: getCustomerById exposes the repository's Optional result.
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findById(99L);
    }

    @Test
    void createCustomer_WhenCreateShouldReturnSuccessfully()
    {
        // 1. Arrange (Hazırlık)
        Customer inputCustomer = new Customer();
        inputCustomer.setFirstName("Yiğit");
        inputCustomer.setLastName("Aslan"); // Düzeltildi
        inputCustomer.setEmail("yigit@aslan.com");

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L); // Veritabanından ID almış hali
        savedCustomer.setFirstName("Yiğit");
        savedCustomer.setLastName("Aslan");
        savedCustomer.setEmail("yigit@aslan.com");
        savedCustomer.setIsactive(true);
        savedCustomer.setCreateDate(LocalDateTime.now());

        // Repository save metodunun çağrıldığında savedCustomer döneceğini simüle ediyoruz
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // 2. Act (Çalıştırma)
        Customer result = customerService.createCustomer(inputCustomer);

        // 3. Assert (Doğrulama)
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Yiğit", result.getFirstName());
        assertEquals("Aslan", result.getLastName());
        assertTrue(result.getIsactive());
        assertNotNull(result.getCreateDate());

        // Repository'nin save metodunun tam 1 kez çağrıldığını doğruluyoruz
        verify(customerRepository, times(1)).save(any(Customer.class));

    }

    @Test
    void createCustomer_WhenCustomerIsNull_ShouldThrowException() {
        // 1. Act & Assert: Null nesne gönderildiğinde hata fırlatmasını test ediyoruz
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.createCustomer(null);
        });

        // 2. Doğrulama: Kayıt metodunun asla tetiklenmediğini doğruluyoruz
        verify(customerRepository, never()).save(any(Customer.class));
    }
    @Test
    void updateCustomer_WhenCustomerExists_ShouldUpdateAndReturnCustomer() {
        // 1. Arrange (Hazırlık)
        Long customerId = 1L;

        // Mevcut (veritabanında var olan) müşteri
        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setFirstName("EskiAd");
        existingCustomer.setLastName("EskiSoyad");
        existingCustomer.setEmail("eski@mail.com");
        existingCustomer.setPhone("5551112233");
        existingCustomer.setTcNo("12345678901");
        existingCustomer.setIsactive(true);

        // Güncelleme için gönderilen yeni bilgiler
        Customer updateDetails = new Customer();
        updateDetails.setFirstName("Yiğit");
        updateDetails.setLastName("Aslan");
        updateDetails.setEmail("yigit@aslan.com");
        updateDetails.setPhone("5559998877");
        updateDetails.setTcNo("98765432109");
        updateDetails.setIsactive(true);

        // Mockito davranışları:
        // 1. findById çağrıldığında mevcut müşteriyi dön
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        // 2. save çağrıldığında güncellenmiş nesneyi geri dön
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. Act (Çalıştırma)
        LocalDateTime beforeUpdate = LocalDateTime.now();
        Customer updatedResult = customerService.updateCustomer(customerId, updateDetails);
        LocalDateTime afterUpdate = LocalDateTime.now();

        // 3. Assert (Doğrulama)
        assertNotNull(updatedResult);
        assertEquals(customerId, updatedResult.getId());
        assertEquals("Yiğit", updatedResult.getFirstName());
        assertEquals("Aslan", updatedResult.getLastName());
        assertEquals("yigit@aslan.com", updatedResult.getEmail());
        assertEquals("5559998877", updatedResult.getPhone());
        assertEquals("98765432109", updatedResult.getTcNo());
        assertNotNull(updatedResult.getUpdatedDate());
        assertFalse(updatedResult.getUpdatedDate().isBefore(beforeUpdate));
        assertFalse(updatedResult.getUpdatedDate().isAfter(afterUpdate));

        // Repository etkileşimlerini kontrol etme
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomer_WhenCustomerDoesNotExist_ShouldThrowException() {
        // 1. Arrange
        Long customerId = 99L;
        Customer updateDetails = new Customer();
        updateDetails.setFirstName("Test");

        // Bulunamadığında Optional.empty dönecek
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // 2. Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.updateCustomer(customerId, updateDetails);
        });

        assertEquals("Müşteri bulunamadı, ID: " + customerId, exception.getMessage());

        // Save metodunun asla çağrılmadığını doğruluyoruz (çünkü müşteri bulunamadı)
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).save(any(Customer.class));
    }
}
