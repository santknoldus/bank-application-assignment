package com.knoldus.bankapplication

import scala.collection.mutable
import scala.util.Random

case class Transactions(transactionId: Long, accountNumber: Long, transactionType: String, amount: Double)

object Banking {

  val allAccounts: mutable.Map[Long, Double] = mutable.Map()

  //to create new account by allocating random account number and opening balance in account
  def createAccount(openingBalance: Double): mutable.Map[Long, Double] = {
    val accountNumber: Long = Random.nextLong()
    allAccounts += (accountNumber -> openingBalance)
    allAccounts
  }

  //to list all accounts
  def listAccounts(): mutable.Map[Long, Double] = {
    allAccounts
  }

  //to update balance according to the list of transaction having transaction-type(credit/debit)
  def updateBalance(transactions: List[Transactions]): mutable.Map[Long, Double] = {
    transactions.foreach(transaction => {
      val currentAmount: Double = fetchAccountBalance(transaction.accountNumber)
      transaction.transactionType match {
        case "credit" => allAccounts += (transaction.accountNumber -> (currentAmount + transaction.amount))
        case "debit" => allAccounts += (transaction.accountNumber -> (currentAmount - transaction.amount))
        case _ => throw new IllegalAccessException("Invalid transaction type")
      }
    })
    allAccounts
  }

  //to fetch account balance for given account number
  def fetchAccountBalance(accountNumber: Long): Double = {
    allAccounts.getOrElse(accountNumber, 0.0)
  }

  //to delete an account
  def deleteAccount(accountNumber: Long): Boolean = {
    if (allAccounts.keySet.exists(_ == accountNumber)) {
      allAccounts -= accountNumber
      true
    }
    else false
  }
}

