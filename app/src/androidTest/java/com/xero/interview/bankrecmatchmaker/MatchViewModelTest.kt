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
    private val mockMatchAndTransactionRepository: MatchAndTransactionRepository = mock()
    private val viewModel = MatchViewModel()

    @Before
    fun setUpTaskDetailViewModel() {
        viewModel.viewState.observeForever(viewStateObserver)
        viewModel.repository = mockMatchAndTransactionRepository
    }

    @Test
    fun initializeReturnsMatchAndTransaction() {
        val transactionAmount = 700f
        val matchAmount = 5000
        mockMatchRepositoryAmount(Pair(matchAmount, transactionAmount))
        viewModel.initialize()
        verify(viewStateObserver).onChanged(
                MatchItemUIModel.MatchCountUpdated(matchAmount)
        )
        verify(viewStateObserver).onChanged(
                MatchItemUIModel.TransactionAmountUpdated(transactionAmount)
        )
    }

    @Test
    fun incrementMatchReturnsUpdatedCount() {
        val previousCount = 1000
        stubMatchRepositoryGetMatch(previousCount)
        viewModel.incrementMatchCount()

        verify(viewStateObserver).onChanged(
                MatchItemUIModel.MatchCountUpdated(previousCount + 1)
        )
    }

    @Test
    fun decrementMatchReturnsUpdatedCount() {
        val previousMatch= 1000
        stubMatchRepositoryGetMatch(previousMatch)
        viewModel.decrementMatchCount()

        verify(viewStateObserver).onChanged(
                MatchItemUIModel.MatchCountUpdated(previousMatch - 1)
        )
    }

    @Test
    fun incrementTransactionReturnsUpdatedAmount() {
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
    fun decrementTransactionReturnsUpdatedAmount() {
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

    @Test
    fun resetReturnsMatchAmount() {
        val transactionAmount = 700f
        val matchAmount = 5000
        mockMatchRepositoryAmount(Pair(matchAmount, transactionAmount))
        viewModel.reset()

        verify(viewStateObserver).onChanged(MatchItemUIModel.MatchCountUpdated(
                matchAmount
        ))
    }

    @Test
    fun resetReturnsTransactionAmount() {
        val transactionAmount = 700f
        val matchAmount = 5000
        mockMatchRepositoryAmount(Pair(matchAmount, transactionAmount))
        viewModel.reset()

        verify(viewStateObserver).onChanged(MatchItemUIModel.TransactionAmountUpdated(
                transactionAmount
        ))
    }

    private fun mockMatchRepositoryAmount(TransactionAndMatch: Pair<Int, Float>) {
        stubMatchRepositoryGetMatch(TransactionAndMatch.first)
        stubMatchRepositoryGetTransaction(TransactionAndMatch.second)
        whenever(mockMatchAndTransactionRepository.getMatchCountAndTotal())
                .thenReturn(TransactionAndMatch)
    }

    private fun stubMatchRepositoryGetTransaction(transaction: Float) {
        whenever(mockMatchAndTransactionRepository.getTransactionTotal())
                .thenReturn(transaction)
    }

    private fun stubMatchRepositoryGetMatch(match: Int) {
        whenever(mockMatchAndTransactionRepository.getMatchCount())
                .thenReturn(match)
    }

}