package com.example.pacecalculator

import data.HistoricDao
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import presentation.HistoricListViewModel

class HistoricListViewModelTest {

    private val historicDao: HistoricDao = mock()

    private val underTest: HistoricListViewModel by lazy {
        HistoricListViewModel(historicDao)
    }

    // Testes cases

    @Test
    fun delete_all()= runTest{
        //Given
        //when
        underTest.deleteAll()
        // Then
        verify(historicDao).deleteAll()
    }

    @Test
    fun delete_by_id() = runTest{
        val itemId = Int.MAX_VALUE
       underTest.deleteById(itemId)
        verify(historicDao).deleteById(itemId)
    }
}

