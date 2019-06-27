package com.xero.interview.bankrecmatchmaker

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import org.junit.Rule
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test

class MatchViewModelTest {


    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewStateObserver: Observer<MatchItemUIModel> = mock()
    private val mockVictoryRepository: MatchAndTransactionRepository = mock()
    private val viewModel = MatchViewModel()

    @Before
    fun setUpTaskDetailViewModel() {
        viewModel.viewState.observeForever(viewStateObserver)
        viewModel.repository = mockVictoryRepository
    }

    @Test
    fun initializeReturnsTitle() {
        val transactionAmount = 700f
        val matchAmount = 5000
        stubVictoryRepositoryGetVictoryTitleAndCount(Pair(matchAmount, transactionAmount))
        viewModel.initialize()
        verify(viewStateObserver).onChanged(
                MatchItemUIModel.MatchCountUpdated(matchAmount)
        )
    }

    private fun stubVictoryRepositoryGetVictoryTitleAndCount(TransactionAndMatch: Pair<Int, Float>) {
        stubMatchRepositoryGetMatch(TransactionAndMatch.first)
        stubMatchRepositoryGetTransaction(TransactionAndMatch.second)
        whenever(mockVictoryRepository.getMatchCountAndTotal())
                .thenReturn(TransactionAndMatch)
    }

    private fun stubMatchRepositoryGetTransaction(transaction: Float) {
        whenever(mockVictoryRepository.getTransactionTotal())
                .thenReturn(transaction)
    }

    private fun stubMatchRepositoryGetMatch(match: Int) {
        whenever(mockVictoryRepository.getMatchCount())
                .thenReturn(match)
    }

}