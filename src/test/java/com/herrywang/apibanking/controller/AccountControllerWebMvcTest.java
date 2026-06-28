package com.herrywang.apibanking.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.herrywang.apibanking.model.Account;
import com.herrywang.apibanking.service.AccountService;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(AccountController.class)
class AccountControllerWebMvcTest {

	@Autowired
	private MockMvcTester mockMvcTester;

	@MockitoBean
	private AccountService accountService;

	@Test
	void createAccount() {
		// Given
		final Account account = Account.builder()
				.customerName("John Doe")
				.accountNickname("SAVINGS")
				.build();

		final Account createdAccount = Account.builder()
				.id(1)
				.customerName("John Doe")
				.accountNickname("SAVINGS")
				.accountNumber("1234567890123456")
				.build();

		when(accountService.createAccount(any(Account.class))).thenReturn(createdAccount);

		// When & Then
		assertThat(mockMvcTester.post().uri("/api/account/createAccount")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(account)))
				.hasStatusOk()
				.bodyJson()
				.hasPathSatisfying("$.id", path -> assertThat(path).isEqualTo(1))
				.hasPathSatisfying("$.customerName", path -> assertThat(path).isEqualTo("John Doe"))
				.hasPathSatisfying("$.accountNickname", path -> assertThat(path).isEqualTo("SAVINGS"))
				.hasPathSatisfying("$.accountNumber", path -> assertThat(path).isEqualTo("1234567890123456"));
	}
}