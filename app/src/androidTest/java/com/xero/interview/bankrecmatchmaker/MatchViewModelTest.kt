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
    fun initializeReturnsMatchAndTransaction() {
        val transactionAmount = 700f
        val matchAmount = 5000
        stubVictoryRepositoryGetVictoryTitleAndCount(Pair(matchAmount, transactionAmount))
        viewModel.initialize()
        verify(viewStateObserver).onChanged(
                MatchItemUIModel.MatchCountUpdated(matchAmount)
        )
        verify(viewStateObserver).onChanged(
                MatchItemUIModel.TransactionAmountUpdated(transactionAmount)
        )
    }

    @Test
    fun incrementMatchReturnsUpatedCount() {
        val previousCount = 1000
        stubMatchRepositoryGetMatch(previousCount)
        viewModel.incrementMatchCount()

        verify(viewStateObserver).onChanged(
                MatchItemUIModel.MatchCountUpdated(previousCount + 1)
        )
    }

    @Test
    fun incrementTransactionReturnsUpatedAmount() {
        val previousAmount = 500f
        val amountToAdd = 10.0f
        stubMatchRepositoryGetTransaction(previousAmount)
        viewModel.addToTransactionAmount(amountToAdd)

        verify(viewStateObserver).onChanged(
                MatchItemUIModel.TransactionAmountUpdated(
                        previousAmount + amountToAdd
                )
        )
    }

    @Test
    fun decrementTransactionReturnsUpatedAmount() {
        val previousAmount = 500f
        val amountToSubtract = 10.0f
        stubMatchRepositoryGetTransaction(previousAmount)
        viewModel.subtractFromTransactionAmount(amountToSubtract)

        verify(viewStateObserver).onChanged(
                MatchItemUIModel.TransactionAmountUpdated(
                        previousAmount - amountToSubtract
                )
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