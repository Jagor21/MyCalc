package com.jagor.mycalc.ui

sealed class CalcOperation(val symbol: String)
object Add: CalcOperation("+")
object Subtract: CalcOperation("-")
object Multiply: CalcOperation("*")
object Divide: CalcOperation("/")

