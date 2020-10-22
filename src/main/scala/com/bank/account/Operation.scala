package com.bank.account

import java.time.LocalDateTime

import com.bank.account.OperationType.OperationType

case class Operation(id:String, date: LocalDateTime, operationType:OperationType,amount:Int, balance:Int)
