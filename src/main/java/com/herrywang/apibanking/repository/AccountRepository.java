package com.herrywang.apibanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.herrywang.apibanking.repository.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

	int countAccountEntitiesByCustomerId(long customerId);
	boolean existsAccountEntitiesByCustomerIdAndAccountNickname(long customerId, String accountNickname);
}
