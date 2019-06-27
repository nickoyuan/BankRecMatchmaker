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
        repository.clear()

        val (title, count) = repository.getMatchCountAndTotal()
        viewState.value = MatchItemUIModel.TransactionAmountUpdated(count)
        viewState.value = MatchItemUIModel.MatchCountUpdated(title)
    }

    fun findSubsetOfTransactionSum(list: List<MatchItem>): MutableList<Int>? {
        val transactionTotal: Int = ((repository.getTransactionTotal() * 100).toInt())
        val transactionList: MutableList<Int> = ArrayList()
        for (transactions in list) {
            transactionList.add((transactions.getTotal() * 100).toInt())
        }
        var subSets = SubsetSumFinder().getSubset(
                transactionList,
                transactionList.size,
                transactionTotal
        )
        var checkBoxSelected: MutableList<Int> = ArrayList()
        if (subSets != null) {
            for (items in subSets) {
                checkBoxSelected.add(transactionList.indexOf(items))
            }
        }
        return checkBoxSelected
    }
}