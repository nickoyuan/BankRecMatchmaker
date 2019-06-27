package com.xero.interview.bankrecmatchmaker

import java.util.ArrayList

interface Calculations {
    fun getAllSubsets(
            transactionArray : List<Int>,
            arrayLength : Int,
            transactionSumToFind : Int
    )
    fun display(subsets : ArrayList<Int>): ArrayList<Int>
}