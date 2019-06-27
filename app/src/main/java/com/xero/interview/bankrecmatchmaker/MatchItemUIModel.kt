package com.xero.interview.bankrecmatchmaker

sealed class MatchItemUIModel {

    data class TransactionAmountUpdated(val amount : Float) : MatchItemUIModel()

    data class MatchCountUpdated(val matchCount: Int) : MatchItemUIModel()
}