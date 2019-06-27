package com.xero.interview.bankrecmatchmaker

interface MatchAndTransactionRepository {
    fun getMatchCountAndTotal() : Pair<Int, Float>

    fun getMatchCount() : Int
    fun setMatchCount(count: Int)

    fun getTransactionTotal() : Float
    fun setTransactionTotal(transactionAmount : Float)

    fun clear()
}