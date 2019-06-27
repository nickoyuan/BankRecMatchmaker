package com.xero.interview.bankrecmatchmaker

interface MatchRepository {
    fun getMatchCountAndTotal() : Pair<Int, Float>

    fun getMatchCount() : Int
    fun setMatchCount(count: Int)

    fun setMatchTotal() : Float
    fun getMatchTotal(amount : Float)

    fun clear()
}