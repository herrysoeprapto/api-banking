package com.herrywang.apibanking.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.herrywang.apibanking.exception.ValidationException;
import com.herrywang.apibanking.mapper.AccountMapper;
import com.herrywang.apibanking.model.Account;
import com.herrywang.apibanking.repository.AccountRepository;
import com.herrywang.apibanking.repository.CustomerRepository;
import com.herrywang.apibanking.repository.entity.AccountEntity;
import com.herrywang.apibanking.repository.entity.CustomerEntity;
import com.herrywang.apibanking.validator.AccountValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private static final String NUMERIC = "0123456789";
	private static final SecureRandom secureRandom = new SecureRandom();

	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;
	private final AccountValidator accountValidator;
	private final AccountMapper accountMapper;

	@CacheEvict(cacheManager = "cacheManager", value = "accountsCache", key = "#p0.customerName")
	public Account createAccount(final Account account) {
		accountValidator.validate(account);

		final Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByCustomerName(account.getCustomerName());
		// insert customer when no customer record is found
		final CustomerEntity customerEntity = optionalCustomerEntity.orElseGet(() -> customerRepository.save(accountMapper.mapAccountToCustomerEntity(account)));
		// insert account
		account.setAccountNumber(generateAccountNumber());
		final AccountEntity accountEntity = accountRepository.save(accountMapper.mapAccountToAccountEntity(account, customerEntity));

		return accountMapper.mapEntityToAccount(accountEntity, customerEntity);
	}


	@Cacheable(cacheManager = "cacheManager", value = "accountsCache")
	public List<Account> getAccounts(final String customerName) {
		final Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByCustomerName(customerName);
		if (optionalCustomerEntity.isEmpty()) {
			// TODO move error message to messages.properties
			throw new ValidationException("Customer name not found");
		}
		final CustomerEntity customerEntity = optionalCustomerEntity.get();
		return accountMapper.mapEntitiesToAccounts(accountRepository.getAccountEntitiesByCustomerId(customerEntity), customerEntity);
	}

	String generateAccountNumber() {
		// TODO implement generate account number
		// TODO perform duplicate check
		final int length = 16;
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = secureRandom.nextInt(NUMERIC.length());
			sb.append(NUMERIC.charAt(index));
		}
		return sb.toString();
	}
}
