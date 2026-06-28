package com.herrywang.apibanking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.herrywang.apibanking.repository.entity.AccountEntity;
import com.herrywang.apibanking.repository.entity.CustomerEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

	int countAccountEntitiesByCustomerId(CustomerEntity customerEntity);
	List<AccountEntity> getAccountEntitiesByCustomerId(CustomerEntity customerEntity);
}
