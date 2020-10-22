package com.bank.account

import java.time.LocalDateTime
import java.util.UUID._

import com.bank.account.OperationType.OperationType

import scala.collection.mutable.ArrayBuffer
import OperationType._
class Account(private var id: String, private var operations: ArrayBuffer[Operation]) {


  def saveMoney(amount: Int): Option[Operation] = {
    val currentDateTime = LocalDateTime.now()
    val balance = calculateBalance(amount, DEPOSIT)
    val operation = Operation(randomUUID.toString, currentDateTime, DEPOSIT, amount, balance)
    operations += operation
    Option(operation)
  }

  def retrieveMoney(amount: Int): Option[Operation] = {
    calculateBalance(amount, WITHDRAWAL) match {
      case balance if balance >= 0 => {
        val currentDateTime = LocalDateTime.now()
        val operation = Operation(randomUUID.toString, currentDateTime, WITHDRAWAL, amount, balance)
        operations += operation
        Option(operation)
      }
      case _ => Option.empty
    }
  }

    def retrieveAll(): Option[Operation] = {
      operations.length match {
        case 0 => Option.empty
        case _ => retrieveMoney(operations.last.balance)
      }
    }

    def calculateBalance(amount: Int, operationType: OperationType): Int = {
      operations.length match {
        case 0 => operationType match {
          case DEPOSIT => amount
          case WITHDRAWAL => -amount
        }
        case _ => operationType match {
          case DEPOSIT => operations.last.balance + amount
          case WITHDRAWAL => operations.last.balance - amount
        }
      }
    }

    def checkOperations(): ArrayBuffer[Operation] = {
      operations.clone()
    }
  }
