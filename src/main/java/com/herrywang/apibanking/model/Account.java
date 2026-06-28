package com.herrywang.apibanking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
	private long id;

	private String customerName;

	private String accountNickname;

	private String accountNumber;
}
