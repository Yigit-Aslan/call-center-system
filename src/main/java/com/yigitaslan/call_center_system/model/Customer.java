package com.yigitaslan.call_center_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(name = "tc_no", unique = true, length = 11)
    private String tcNo;

    private Boolean isactive = true;

    @Column(name = "createdate")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "updateddate")
    private LocalDateTime updatedDate = LocalDateTime.now();
}