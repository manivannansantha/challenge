package com.db.awmd.challenge.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transfer;
import com.db.awmd.challenge.exception.AccountIdNotFoundException;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InSufficientBalanceException;
import com.db.awmd.challenge.service.AccountsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/accounts")
@Slf4j
public class AccountsController {

  private final AccountsService accountsService;

  @Autowired
  public AccountsController(AccountsService accountsService) {
    this.accountsService = accountsService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createAccount(@RequestBody @Valid Account account) {
    log.info("Creating account {}", account);

    try {
    this.accountsService.createAccount(account);
    } catch (DuplicateAccountIdException daie) {
      return new ResponseEntity<>(daie.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping(path = "/{accountId}")
  public Account getAccount(@PathVariable String accountId) {
    log.info("Retrieving account for id {}", accountId);
    return this.accountsService.getAccount(accountId);
  }

  /**
   * moneyTransfer method used for transfer the money between two accountIds
   * used the transfer object to pack the values
   * Put method used to update the account balance
   * 
   * @param transfer
   * @return
   */
  @PutMapping(path = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> moneyTransfer(@RequestBody @Valid Transfer transfer) {
	  log.info("Money transfer {}", transfer);

    try {
    	
    this.accountsService.moneyTransfer(transfer);
    } catch (final AccountIdNotFoundException aie) {
      return new ResponseEntity<>(aie.getMessage(), HttpStatus.BAD_REQUEST);
    }catch (final InSufficientBalanceException inSufficientBalanceException) {
        return new ResponseEntity<>(inSufficientBalanceException.getMessage(), HttpStatus.BAD_REQUEST);
      }

    return new ResponseEntity<>("Funds transfered successfully fromAccountId "+transfer.getFromAccountId() +" toAccountId "+transfer.getToAccountId() ,HttpStatus.UPGRADE_REQUIRED);
  }
  
}
