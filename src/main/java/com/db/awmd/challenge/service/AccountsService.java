package com.db.awmd.challenge.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transfer;
import com.db.awmd.challenge.exception.AccountIdNotFoundException;
import com.db.awmd.challenge.exception.InSufficientBalanceException;
import com.db.awmd.challenge.repository.AccountsRepository;

import lombok.Getter;

@Service
public class AccountsService {

	private String fromAccountId;
	private String toAccountId;
	private BigDecimal transferAmount;

	private Account fromAccount;
	private Account toAccount;

	@Getter
	private final AccountsRepository accountsRepository;

	@Autowired
	public AccountsService(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	public void createAccount(Account account) {
		this.accountsRepository.createAccount(account);
	}

	public Account getAccount(String accountId) {
		return this.accountsRepository.getAccount(accountId);
	}

	 /**
	   * moneyTransfer method used for transfer the money between two accountIds
	   * used the transfer object to pack the values
	   * get the fromAccountId and toAccountId account object and check the object
	   * Verify the from account balance
	   * 
	   * @param transfer
	   * @return
	   */
	
	public void moneyTransfer(final Transfer transfer) {

		this.fromAccountId = transfer.getFromAccountId();
		this.toAccountId = transfer.getToAccountId();
		this.transferAmount = transfer.getAmount();

		fromAccount = this.accountsRepository.getAccount(fromAccountId);
		toAccount = this.accountsRepository.getAccount(toAccountId);

		if (null == fromAccount) {
			throw new AccountIdNotFoundException("Account id " + this.fromAccountId + " does not exit");
		}

		if (null == toAccount) {
			throw new AccountIdNotFoundException("Account id " + this.toAccountId + " does not exit");
		}

		if (fromAccount.getBalance().compareTo(transferAmount) >= 0) {
			toAccount.setBalance(toAccount.getBalance().add(transferAmount));
			fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
			this.accountsRepository.updateAccount(toAccount);
			this.accountsRepository.updateAccount(fromAccount);

		} else {
			throw new InSufficientBalanceException(
					"From Account id " + this.fromAccountId + " insufficient money so not able to transfer money");
		}

	}
}
