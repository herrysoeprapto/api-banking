package com.herrywang.apibanking.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.herrywang.apibanking.model.Account;
import com.herrywang.apibanking.repository.entity.AccountEntity;
import com.herrywang.apibanking.repository.entity.CustomerEntity;

@Component
public class AccountMapper {

	public Account mapEntityToAccount(final AccountEntity accountEntity, final CustomerEntity customerEntity) {
		return Account.builder()
				.customerName(customerEntity.getCustomerName())
				.id(accountEntity.getId())
				.accountNumber(accountEntity.getAccountNumber())
				.accountNickname(accountEntity.getAccountNickname())
				.build();
	}

	public List<Account> mapEntitiesToAccounts(final List<AccountEntity> accountEntities, final CustomerEntity customerEntity) {
		return accountEntities.stream()
				.map(accountEntity -> mapEntityToAccount(accountEntity, customerEntity))
				.toList();
	}

	public CustomerEntity mapAccountToCustomerEntity(final Account account) {
		return CustomerEntity.builder()
				.customerName(account.getCustomerName())
				.build();
	}

	public AccountEntity mapAccountToAccountEntity(final Account account, final CustomerEntity customerEntity) {
		return AccountEntity.builder()
				.customerId(customerEntity)
				.accountNickname(account.getAccountNickname())
				.accountNumber(account.getAccountNumber())
				.build();
	}
}
