package com.xero.interview.bankrecmatchmaker

import java.util.ArrayList

class SubsetSumFinder: Calculations {

    companion object {
        lateinit var dp: Array<BooleanArray>
    }

    override fun getAllSubsets(
            transactionArray : List<Int>,
            arrayLength : Int,
            transactionSumToFind : Int
    ) {

        if (arrayLength == 0 || transactionSumToFind < 0)
                return

        // Sum 0 can always be achieved with 0 elements
        dp = Array(arrayLength) {
            BooleanArray(
                transactionSumToFind + 1
                )
        }

        for (i in 0 until arrayLength) {
            dp[i][0] = true
        }

        // Sum arr[0] can be achieved with single element
        if (transactionArray[0] <= transactionSumToFind)
            dp[0][transactionArray[0]] = true

        // Fill rest of the entries in dp[][]
        for (i in 1 until arrayLength) {
            var j = 0
            while (j < transactionSumToFind + 1) {
                dp[i][j] = if (transactionArray[i] <= j)
                    dp[i - 1][j] || dp[i - 1][(j - transactionArray[i])]
                else
                    dp[i - 1][j]
                ++j
            }
        }
        if (dp[arrayLength - 1][transactionSumToFind] == false) {
            println("There are no subsets with" +
                    " sum " + transactionSumToFind)
            return
        }

        // Now recursively traverse dp[][] to find all
        // paths from dp[n-1][sum]
        val arrayList = ArrayList<Int>()
        return printSubsetsRec(
                transactionArray,
                arrayLength- 1,
                transactionSumToFind,
                arrayList
        )
    }

    fun printSubsetsRec(
            transactionArray: List<Int>,
            arrayLength : Int,
            transactionSumToFind : Int,
            arrayList : ArrayList<Int>
    )
    {
        // If we reached end and sum is non-zero. We print
        // p[] only if arr[0] is equal to sun OR dp[0][sum]
        // is true.
        if (arrayLength == 0 && transactionSumToFind != 0 && dp[0][transactionSumToFind]) {
            arrayList.add(transactionArray[arrayLength])
            display(arrayList)
            arrayList.clear()
            return
        }

        // If sum becomes 0
        if (arrayLength == 0 && transactionSumToFind == 0) {
            display(arrayList)
            arrayList.clear()
            return
        }

        // If given sum can be achieved after ignoring
        // current element.
        if (dp[arrayLength - 1][transactionSumToFind]) {
            // Create a new vector to store path
            val createSeparateArray = ArrayList<Int>()
            createSeparateArray.addAll(arrayList)
            printSubsetsRec(
                    transactionArray,
                    arrayLength - 1,
                    transactionSumToFind,
                    createSeparateArray
            )
        }

        // If given sum can be achieved after considering
        // current element.
        if (transactionSumToFind >= transactionArray[arrayLength]
                && dp[arrayLength- 1][(transactionSumToFind - transactionArray[arrayLength])]
        ) {
            arrayList.add(transactionArray[arrayLength])
            printSubsetsRec(
                    transactionArray,
                    arrayLength - 1,
                    transactionSumToFind - transactionArray[arrayLength],
                    arrayList
            )
        }
    }

    override fun display(subsets : ArrayList<Int>): ArrayList<Int> {
         return subsets
    }


}