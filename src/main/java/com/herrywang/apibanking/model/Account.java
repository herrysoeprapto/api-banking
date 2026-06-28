package com.herrywang.apibanking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	private long id;

	private String customerName;

	private String accountNickname;

	private String accountNumber;
}
