package com.example.pacecalculator

import org.junit.Test

import org.junit.Assert.*
import presentation.CalculatorViewModel

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val underTest = CalculatorViewModel()
    @Test
    fun addition_isCorrect() {

        val result = underTest.resultRitm(2f, 3f)
        assertEquals(0.6666667f, result)
    }
}