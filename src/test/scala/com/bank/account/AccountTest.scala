package com.bank.account

import java.time.LocalDateTime

import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.mutable.ArrayBuffer

class AccountTest extends AnyFunSuite with BeforeAndAfter {


  test("save money") {
    //Given
    val account: Account = new Account("10000",new ArrayBuffer[Operation])
    //when
    account.saveMoney(500)
    //then
    assert(account.checkOperations().length == 1)
    assert(account.checkOperations()(0).amount == 500)
    assert(account.checkOperations()(0).operationType == OperationType.DEPOSIT)

  }

  test("retrieve money") {
    //Given
    val operation = Operation("111", LocalDateTime.now(), OperationType.WITHDRAWAL, 200, 500)
    val account: Account = new Account("11111",ArrayBuffer(operation))
    //when
    val result = account.retrieveMoney(250)
    //then
    assert(account.checkOperations().length == 2)
    assert(result != None)
    assert(result.map(_.balance) == Some(250))
  }

  test("retrieve money from empty account") {
    //Given
    val account: Account = new Account("11111",new ArrayBuffer[Operation])
    //when
    val result = account.retrieveMoney(500)
    //then
    assert(account.checkOperations().length == 0)
    assert(result== Option.empty)
  }

  test("calculate balance") {
    //Given
    val account: Account = new Account("10000",new ArrayBuffer[Operation])
    //when
    account.saveMoney(500)
    account.saveMoney(600)
    account.saveMoney(200)
    account.saveMoney(100)
    account.retrieveMoney(20)
    account.retrieveMoney(50)
    account.retrieveMoney(10)
    account.retrieveMoney(60)
    //then
    assert(account.checkOperations().length == 8)
    assert(account.checkOperations()(0).balance == 500)
    assert(account.checkOperations()(1).balance == 1100)
    assert(account.checkOperations()(2).balance == 1300)
    assert(account.checkOperations()(3).balance == 1400)
    assert(account.checkOperations()(4).balance == 1380)
    assert(account.checkOperations()(5).balance == 1330)
    assert(account.checkOperations()(6).balance == 1320)
    assert(account.checkOperations()(7).balance == 1260)
  }

  test("retrieve all") {
    //Given
    val operation1 = Operation("111", LocalDateTime.now(), OperationType.WITHDRAWAL, 200, 2000)
    val operation2 = Operation("112", LocalDateTime.now(), OperationType.WITHDRAWAL, 200, 500)
    val operation3 = Operation("113", LocalDateTime.now(), OperationType.WITHDRAWAL, 200, 2500)
    val account: Account = new Account("11111",ArrayBuffer(operation1,operation2,operation3))
    //when
    val result = account.retrieveMoney(2500)
    //then
    assert(account.checkOperations().length == 4)
    assert(result != None)
    assert(result.map(_.balance) == Some(0))
  }
}
