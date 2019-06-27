package com.xero.interview.bankrecmatchmaker

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


class MatchViewModel : ViewModel() {

    val viewState: MutableLiveData<MatchItemUIModel> = MutableLiveData()
    lateinit var repository: MatchAndTransactionRepository

    fun initialize() {
        val (matchCount, transactionAmount) = repository.getMatchCountAndTotal()
        viewState.value = MatchItemUIModel.TransactionAmountUpdated(transactionAmount)
        viewState.value = MatchItemUIModel.MatchCountUpdated(matchCount)
    }

    fun setMatchCount(count: Int) {
        repository.setMatchCount(count)
        viewState.value = MatchItemUIModel.MatchCountUpdated(count)
    }

    fun decrementMatchCount() {
        val newCount = repository.getMatchCount() - 1
        repository.setMatchCount(newCount)
        viewState.value = MatchItemUIModel.MatchCountUpdated(newCount)
    }

    fun incrementMatchCount() {
        val newCount = repository.getMatchCount() + 1
        repository.setMatchCount(newCount)
        viewState.value = MatchItemUIModel.MatchCountUpdated(newCount)
    }

    fun setTransactionAmount(amount : Float) {
        repository.setTransactionTotal(amount)
        viewState.value = MatchItemUIModel.TransactionAmountUpdated(amount)
    }

    fun subtractFromTransactionAmount(amountToSubtract : Float) {
        val newAmount = repository.getTransactionTotal() - amountToSubtract
        repository.setTransactionTotal(newAmount)
        viewState.value = MatchItemUIModel.TransactionAmountUpdated(newAmount)
    }

    fun addToTransactionAmount(amountToAdd : Float) {
        val newAmount = repository.getTransactionTotal() + amountToAdd
        repository.setTransactionTotal(newAmount)
        viewState.value = MatchItemUIModel.TransactionAmountUpdated(newAmount)
    }
    
    fun reset() {
        // TODO reset existing victory title and count
    }
}