package com.jagor.mycalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jagor.mycalc.ui.*
import com.jagor.mycalc.ui.Number

class CalcViewModel : ViewModel() {

    var state by mutableStateOf(CalcState())
        private set

    fun onAction(calcAction: CalcAction) {
        when (calcAction) {
            is Number -> enterNumber(calcAction.number)
            is Decimal -> enterDecimal()
            is Clear -> state = CalcState()
            is Operation -> enterOperation(calcAction.operation)
            is Calculate -> performCalculation()
            is Delete -> performDeletion()
        }
    }

    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> state = state.copy(number2 = state.number2.dropLast(1))
            state.operation != null -> state = state.copy(operation = null)
            state.number1.isNotBlank() -> state = state.copy(number1 = state.number1.dropLast(1))
        }
    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()

        if (number1 != null && number2 != null) {
            val result = when (state.operation) {
                is Add -> number1 + number2
                is Subtract -> number1 - number2
                is Multiply -> number1 * number2
                is Divide -> number1 / number2
                null -> return
            }

            state = state.copy(
                number1 = result.toString().take(15),
                number2 = "",
                operation = null
            )
        }
    }

    private fun enterOperation(operation: CalcOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && !state.number1.contains(".") && state.number1.isNotBlank()) {
            state = state.copy(number1 = state.number1 + ".")
            return
        }
        if (!state.number2.contains(".") && state.number2.isNotBlank()) {
            state = state.copy(number2 = state.number2 + ".")
            return
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            if (state.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(number1 = state.number1 + number)
            return
        }

        if (state.number2.length >= MAX_NUM_LENGTH) {
            return
        }
        state = state.copy(number2 = state.number2 + number)
        return
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }


}