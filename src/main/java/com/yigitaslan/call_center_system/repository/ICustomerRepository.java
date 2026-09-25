package com.yigitaslan.call_center_system.repository;

import com.yigitaslan.call_center_system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long>{
}
