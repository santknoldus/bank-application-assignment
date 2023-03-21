package com.knoldus.bankapplication
import scala.collection.mutable
import org.scalatest.funsuite.AnyFunSuite

class BankingTest extends AnyFunSuite {

  val account1: mutable.Map[Long, Double] = Banking.createAccount(1000.0)
  val account2: mutable.Map[Long, Double] = Banking.createAccount(2000.0)
  val account3: mutable.Map[Long, Double] = Banking.createAccount(3000.0)

  val listOfAccountNumber: List[Long] = Banking.allAccounts.keySet.toList

  val listOfTransactions: List[Transactions] = List(
    Transactions(1, listOfAccountNumber(0), "credit", 100.0),
    Transactions(2, listOfAccountNumber(0), "debit", 50.0),
    Transactions(3, listOfAccountNumber(1), "credit", 500.0),
    Transactions(4, listOfAccountNumber(1), "debit", 200.0),
    Transactions(5, listOfAccountNumber(2), "credit", 1000.0),
    Transactions(6, listOfAccountNumber(2), "debit", 300.0)
  )

  test("listAccounts should return all accounts with balance") {
    assert(Banking.listAccounts() == Map(listOfAccountNumber(0) -> Banking.allAccounts(listOfAccountNumber(0)),
      listOfAccountNumber(1) -> Banking.allAccounts(listOfAccountNumber(1)),
      listOfAccountNumber(2) -> Banking.allAccounts(listOfAccountNumber(2))))
  }
  test("fetchAccountBalance should return account balance for a given account number") {
    assert(Banking.fetchAccountBalance(listOfAccountNumber(0)) == Banking.allAccounts(listOfAccountNumber(0)))
    assert(Banking.fetchAccountBalance(listOfAccountNumber(1)) == Banking.allAccounts(listOfAccountNumber(1)))
    assert(Banking.fetchAccountBalance(listOfAccountNumber(2)) == Banking.allAccounts(listOfAccountNumber(2)))
  }
  test("updateBalance should update account balance for a given list of transactions") {
    val currentBalance = Banking.allAccounts(listOfAccountNumber(0))
    Banking.updateBalance(listOfTransactions)
    val updatedBalance = currentBalance+listOfTransactions(0).amount-listOfTransactions(1).amount
    assert(Banking.allAccounts(listOfAccountNumber(0)) == updatedBalance)
  }
  test("deleteAccount should delete an account for a given account number") {
    assert(Banking.deleteAccount(listOfAccountNumber(2)))
    assert(Banking.listAccounts() == Map(listOfAccountNumber(0) -> Banking.allAccounts(listOfAccountNumber(0)),
      listOfAccountNumber(1) -> Banking.allAccounts(listOfAccountNumber(1))))
  }

}
