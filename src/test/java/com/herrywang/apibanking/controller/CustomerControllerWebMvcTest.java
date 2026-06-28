package com.herrywang.apibanking.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.herrywang.apibanking.model.Account;
import com.herrywang.apibanking.service.AccountService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(CustomerController.class)
class CustomerControllerWebMvcTest {

	@Autowired
	private MockMvcTester mockMvcTester;

	@MockitoBean
	private AccountService accountService;

	@Test
	void getAccounts() {
		// Given
		final Account account1 = Account.builder()
				.id(1)
				.customerName("John Doe")
				.accountNickname("SAVINGS")
				.accountNumber("1234567890123456")
				.build();
		final Account account2 = Account.builder()
				.id(2)
				.customerName("John Doe")
				.accountNickname("CURRENT")
				.accountNumber("1234567890111111")
				.build();
		when(accountService.getAccounts(anyString())).thenReturn(List.of(account1, account2));

		// When / Then
		assertThat(mockMvcTester.get().uri("/api/customer/John Doe/getAccounts"))
				.hasStatusOk()
				.bodyJson()
				.hasPathSatisfying("$[0].id", path -> assertThat(path).isEqualTo(1))
				.hasPathSatisfying("$[0].customerName", path -> assertThat(path).isEqualTo("John Doe"))
				.hasPathSatisfying("$[0].accountNickname", path -> assertThat(path).isEqualTo("SAVINGS"))
				.hasPathSatisfying("$[0].accountNumber", path -> assertThat(path).isEqualTo("1234567890123456"))
				.hasPathSatisfying("$[1].id", path -> assertThat(path).isEqualTo(2))
				.hasPathSatisfying("$[1].customerName", path -> assertThat(path).isEqualTo("John Doe"))
				.hasPathSatisfying("$[1].accountNickname", path -> assertThat(path).isEqualTo("CURRENT"))
				.hasPathSatisfying("$[1].accountNumber", path -> assertThat(path).isEqualTo("1234567890111111"));
	}
}
