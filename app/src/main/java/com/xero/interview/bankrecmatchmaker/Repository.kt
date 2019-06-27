package com.xero.interview.bankrecmatchmaker

import android.content.Context

class Repository(context : Context) : MatchAndTransactionRepository{

    companion object {
        private const val PACKAGE_NAME = "com.xero.interview.bankrecmatchmaker"
        private const val KEY_MATCHES_COUNT = "matches_count"
        private const val KEY_TRANSACTION_COUNT = "transaction_count"
    }

    private val sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE)

    override fun getMatchCountAndTotal(): Pair<Int, Float> {
        return Pair(getMatchCount(), getTransactionTotal())
    }

    override fun getMatchCount(): Int {
        return sharedPreferences.getInt(KEY_MATCHES_COUNT, 10000)
    }

    override fun setMatchCount(count: Int) {
        sharedPreferences.edit().putInt(KEY_MATCHES_COUNT, count).apply()
    }

    override fun getTransactionTotal(): Float {
        return sharedPreferences.getFloat(KEY_TRANSACTION_COUNT, 2000f)
    }

    override fun setTransactionTotal(transactionAmount: Float) {
        sharedPreferences.edit().putFloat(KEY_TRANSACTION_COUNT, transactionAmount).apply()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}