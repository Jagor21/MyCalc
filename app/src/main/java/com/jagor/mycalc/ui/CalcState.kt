package com.jagor.mycalc.ui

data class CalcState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalcOperation? = null
)
