package com.xero.interview.bankrecmatchmaker

import java.util.ArrayList

interface Calculations {
    fun getSubset(
            transactionArray : List<Int>,
            arrayLength : Int,
            transactionSumToFind : Int
    ): ArrayList<Int>?
}