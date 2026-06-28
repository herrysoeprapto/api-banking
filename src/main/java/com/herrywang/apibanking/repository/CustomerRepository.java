package com.herrywang.apibanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.herrywang.apibanking.repository.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

	Optional<CustomerEntity> findByCustomerName(String customerName);
}
