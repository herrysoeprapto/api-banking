package com.herrywang.apibanking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.herrywang.apibanking.model.Account;
import com.herrywang.apibanking.service.AccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

	private final AccountService accountService;

	@GetMapping("/{customerName}/getAccounts")
	public ResponseEntity<List<Account>> getAccounts(@PathVariable("customerName") String customerName) {
		return ResponseEntity.ok().body(accountService.getAccounts(customerName));
	}
}
