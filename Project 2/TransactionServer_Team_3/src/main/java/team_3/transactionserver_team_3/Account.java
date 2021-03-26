/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21
  */

  package team_3.transactionserver_team_3;

  /**
    * Account will be manipulated by Transactions and managed by AccountManager.
    * An Account object holds within it a balance and account number.
    */
  public class Account
  {  
      // Account object specific values
      int accountNum;
      int balance;
  
      Account(int accountNum, int balanceAmount)
      {
          // Initialize Account fields
          
          // set account number
          this.accountNum = accountNum;
          // Set the configurable balance
          this.balance = balanceAmount;
          // Account's number will be initialized to current counter value
      }
      
      public int getBalance()
      {
          return balance;
      }
      
      public void setBalance(int newBalance)
      {
          balance = newBalance;
      }
  }
  