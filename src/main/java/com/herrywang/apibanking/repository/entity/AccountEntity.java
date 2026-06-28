package com.herrywang.apibanking.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
public class AccountEntity {
	@Id
	private long id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private CustomerEntity customerId;

	private String accountNumber;
	private String accountNickname;

	@Version
	private int version;

}
