package com.herrywang.apibanking.validator;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.herrywang.apibanking.exception.ValidationException;
import com.herrywang.apibanking.model.Account;
import com.herrywang.apibanking.repository.AccountRepository;
import com.herrywang.apibanking.repository.CustomerRepository;
import com.herrywang.apibanking.repository.entity.CustomerEntity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountValidatorTest {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private AccountRepository accountRepository;

	private AccountValidator accountValidator;

	@BeforeEach
	void setUp() {
		accountValidator = new AccountValidator(customerRepository, accountRepository);
	}

	@Test
	void validate_WithValidAccount_ShouldPassValidation() {
		// Given
		Account account = Account.builder()
				.customerName("John Doe")
				.accountNickname("My Account")
				.build();

		when(customerRepository.findByCustomerName("John Doe")).thenReturn(Optional.empty());

		// When & Then
		assertDoesNotThrow(() -> accountValidator.validate(account));
	}

	@Test
	void validate_WithInvalidAccountNickname_TooShort() {
		// Given
		Account account = Account.builder()
				.customerName("John Doe")
				.accountNickname("123") // Less than 5 characters
				.build();

		// When & Then
		ValidationException exception = assertThrows(ValidationException.class, 
				() -> accountValidator.validate(account));
		
		assertTrue(exception.getErrors().contains("accountNickname must be between 5 and 30"));
	}

	@Test
	void validate_WithInvalidAccountNickname_TooLong() {
		// Given
		Account account = Account.builder()
				.customerName("John Doe")
				.accountNickname("This nickname is too long and exceeds 30 characters") // More than 30 characters
				.build();

		// When & Then
		ValidationException exception = assertThrows(ValidationException.class, 
				() -> accountValidator.validate(account));
		
		assertTrue(exception.getErrors().contains("accountNickname must be between 5 and 30"));
	}

	@Test
	void validate_WithMissingAccountNickname() {
		// Given
		Account account = Account.builder()
				.customerName("John Doe")
				.accountNickname("") // Empty nickname
				.build();

		// When & Then
		ValidationException exception = assertThrows(ValidationException.class, 
				() -> accountValidator.validate(account));
		
		assertTrue(exception.getErrors().contains("accountNickname is mandatory"));
	}

	@Test
	void validate_WithMissingCustomerName() {
		// Given
		Account account = Account.builder()
				.customerName("") // Empty customer name
				.accountNickname("My Account")
				.build();

		// When & Then
		ValidationException exception = assertThrows(ValidationException.class, 
				() -> accountValidator.validate(account));
		
		assertTrue(exception.getErrors().contains("customerName is mandatory"));
	}

	@Test
	void validate_WithCustomerHavingMaxAccounts() {
		// Given
		Account account = Account.builder()
				.customerName("John Doe")
				.accountNickname("My Account")
				.build();

		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setId(1L);
		
		when(customerRepository.findByCustomerName("John Doe")).thenReturn(Optional.of(customerEntity));
		when(accountRepository.countAccountEntitiesByCustomerId(customerEntity)).thenReturn(5);

		// When & Then
		ValidationException exception = assertThrows(ValidationException.class, 
				() -> accountValidator.validate(account));
		
		assertTrue(exception.getErrors().contains("Customer already has max 5 accounts"));
	}

	@Test
	void validate_WithValidAccountAndExistingCustomerWithLessThanFiveAccounts() {
		// Given
		Account account = Account.builder()
				.customerName("John Doe")
				.accountNickname("My Account")
				.build();

		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setId(1L);
		
		when(customerRepository.findByCustomerName("John Doe")).thenReturn(Optional.of(customerEntity));
		when(accountRepository.countAccountEntitiesByCustomerId(customerEntity)).thenReturn(3); // Less than 5

		// When & Then
		assertDoesNotThrow(() -> accountValidator.validate(account));
	}
}