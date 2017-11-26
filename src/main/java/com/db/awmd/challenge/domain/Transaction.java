package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Transaction {

	@NotNull
	@NotEmpty
	private final String accountFromId;

	@NotNull
	@NotEmpty
	private final String accountToId;

	@NotNull
	private final BigDecimal amount;

	@JsonCreator
	public Transaction(@JsonProperty("accountFromId") String accountFromId,
			@JsonProperty("accountToId") String accountToId, @JsonProperty("amount") BigDecimal amount) {
		this.accountFromId = accountFromId;
		this.accountToId = accountToId;
		this.amount = amount;
	}

}
