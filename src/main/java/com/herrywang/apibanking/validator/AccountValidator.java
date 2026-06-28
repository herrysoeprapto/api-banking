package com.herrywang.apibanking.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.herrywang.apibanking.exception.ValidationException;
import com.herrywang.apibanking.model.Account;
import com.herrywang.apibanking.repository.AccountRepository;
import com.herrywang.apibanking.repository.CustomerRepository;
import com.herrywang.apibanking.repository.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccountValidator {
	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;

	public void validate(final Account account) {
		final List<String> errorMsg = new ArrayList<>();

		//TODO move errorMsg to messages.properties

		// validate account nickname
		if (!StringUtils.hasText(account.getAccountNickname())) {
			errorMsg.add("accountNickname is mandatory");
		} else {
			final int accountNicknameLength = account.getAccountNickname().trim().length();
			if (accountNicknameLength < 5 || accountNicknameLength > 30) {
				errorMsg.add("accountNickname must be between 5 and 30");
			}
		}

		// validate customer name
		if (!StringUtils.hasText(account.getCustomerName())) {
			errorMsg.add("customerName is mandatory");
		}

		if (!errorMsg.isEmpty()) {
			throw new ValidationException(errorMsg);
		}

		final Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findByCustomerName(account.getCustomerName());
		if (optionalCustomerEntity.isPresent()) {
			final CustomerEntity customerEntity = optionalCustomerEntity.get();
			// check that customer does not have max of 5 accounts yet
			if (accountRepository.countAccountEntitiesByCustomerId(customerEntity.getId()) >= 5) {
				errorMsg.add("Customer already has max 5 accounts");
			}
		}

		// TODO validate if nickname is offensive

		if (!errorMsg.isEmpty()) {
			throw new ValidationException(errorMsg);
		}
	}
}
