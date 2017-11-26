package com.db.awmd.challenge.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transaction;
import com.db.awmd.challenge.exception.InsufficientFundsException;
import com.db.awmd.challenge.exception.NegativeAmountException;

import lombok.Getter;

@Service
public class TransactionService {

	@Getter
	private final AccountsService accountsService;

	@Getter
	private final NotificationService notificationService;

	@Autowired
	public TransactionService(AccountsService accountsService, NotificationService notificationService) {
		this.accountsService = accountsService;
		this.notificationService = notificationService;
	}

	public void transferFunds(Transaction transaction) throws InsufficientFundsException, NegativeAmountException {

		if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
			throw new NegativeAmountException("Transfer Amount Always be a Positive Number");
		}
		Account accountFrom = this.accountsService.getAccount(transaction.getAccountFromId());
		Account accountTo = this.accountsService.getAccount(transaction.getAccountToId());
		if (accountFrom.getBalance().compareTo(transaction.getAmount()) == -1) {
			throw new InsufficientFundsException("InsufficientFunds in " + accountFrom.getAccountId() + " Account");
		}
		BigDecimal availableBalance = accountFrom.getBalance().subtract(transaction.getAmount());
		accountFrom.setBalance(availableBalance);
		accountsService.updateAccount(accountFrom);
		notificationService.notifyAboutTransfer(accountFrom, "Amout Transfered successfully and Current balance"+availableBalance);
		BigDecimal updatedBalance = accountTo.getBalance().add(transaction.getAmount());
		accountTo.setBalance(updatedBalance);
		accountsService.updateAccount(accountTo);
		notificationService.notifyAboutTransfer(accountTo, "Amout Credited successfully and Current balance"+updatedBalance);
	}
}
