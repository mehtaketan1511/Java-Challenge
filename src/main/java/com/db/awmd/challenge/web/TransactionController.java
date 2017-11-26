package com.db.awmd.challenge.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.awmd.challenge.domain.Transaction;
import com.db.awmd.challenge.exception.InsufficientFundsException;
import com.db.awmd.challenge.exception.NegativeAmountException;
import com.db.awmd.challenge.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/transaction")
@Slf4j
public class TransactionController {

	private final TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> transferFunds(@RequestBody @Valid Transaction transaction) {
		log.info("get transaction details {}", transaction);

		try {
			this.transactionService.transferFunds(transaction);
		} catch (InsufficientFundsException daie) {
			return new ResponseEntity<>(daie.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NegativeAmountException daie) {
			return new ResponseEntity<>(daie.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
