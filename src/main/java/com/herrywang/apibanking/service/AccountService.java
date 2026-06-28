package com.herrywang.apibanking.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.herrywang.apibanking.model.Account;
import com.herrywang.apibanking.repository.AccountRepository;
import com.herrywang.apibanking.repository.CustomerRepository;
import com.herrywang.apibanking.repository.entity.CustomerEntity;
import com.herrywang.apibanking.validator.AccountValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;
	private final AccountValidator accountValidator;

	public Account createAccount(final Account account) {
		accountValidator.validate(account);

		final Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByCustomerName(account.getCustomerName());
		if (optionalCustomerEntity.isEmpty()) {
			// TODO insert customer
		}
		// TODO insert account
		return account;
	}
}
