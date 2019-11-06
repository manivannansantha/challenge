
1. Added new PUT moneyTransfer() method in AccountsController.java class
2. Added new updateAccount() method in AccountsRepository.java & AccountsRepositoryInMemory.java class
3. Added new moneyTransfer() method in AccountsService.java class (updated all business logic in the moneyTransfer method)
4. Created new domain Transfer.java class for transfer money.(fromAccountId, toAccountId, amount)
5. Created two new exception class ( AccountIdNotFoundException.java, InSufficientBalanceException)
6. Added new Junit Test cases in AccountsControllerTest.java & AccountsServiceTest.java class

Find below payload and URLs

1. Using POST method to create the account
URL :- localhost:18080/v1/accounts

Request Payload : 
{
    "accountId": "101",
    "balance": 5000
}

2. Using GET method to retrieve the account
URL :- localhost:18080/v1/accounts/101 

Response payload :
{
    "accountId": "601",
    "balance": 4900
}

3. Using PUT method to transfer the money
URL :- localhost:18080/v1/accounts/transfer

Request payload:
{
      "fromAccountId" : 101,
      "toAccountId" : 102,
      "amount" : 1000
     
}

