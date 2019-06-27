package com.xero.interview.bankrecmatchmaker

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import android.content.Context
import android.support.test.InstrumentationRegistry.getInstrumentation
import org.junit.After
import org.junit.Before


@LargeTest
class FindMatchActivityTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(FindMatchActivity::class.java)

    private val PACKAGE_NAME = "com.xero.interview.bankrecmatchmaker"

    fun cleanSheredPrefs() {
        val sharedPreferences = getInstrumentation().targetContext.getSharedPreferences(
                PACKAGE_NAME,
                Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    @Before
    fun setUp() {
        cleanSheredPrefs()
    }

    @After
    fun tearDown() {
        cleanSheredPrefs()
    }


    private fun getRVcount(): Int {
        val recyclerView = rule.getActivity().findViewById(R.id.recycler_view) as RecyclerView
        return recyclerView.adapter.itemCount
    }

    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    @Test
    fun tappingOnCheckBoxToCheck() {
        if (getRVcount() > 0) {
            onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                    .check(
                            matches(isNotChecked())
                    )
                    .perform(click())
                    .check(matches(isChecked()))
        }
    }

    @Test
    fun tappingOnCheckBoxToUnCheck() {
        if (getRVcount() > 0) {
            onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                    .check(
                            matches(isNotChecked())
                    )
                    .perform(click())
                    .check(matches(isChecked()))

            onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                    .check(
                            matches(isChecked())
                    )
                    .perform(click())
                    .check(matches(isNotChecked()))

        }
    }

    @Test
    fun unCheckBoxIncrementsMatchCount() {
        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(
                        matches(isNotChecked())
                )
                .perform(click())
                .check(matches(isChecked()))

        val previousCountString = rule.activity.findViewById<TextView>(R.id.match_count).text.toString()
        val previousCount = if (previousCountString.isBlank()) 0 else previousCountString.toInt()

        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(
                        matches(isChecked())
                )
                .perform(click())
                .check(matches(isNotChecked()))

        onView(CoreMatchers.allOf(withId(R.id.match_count), withText((previousCount+1).toString())))
                .check(matches(isDisplayed()))
    }

    @Test
    fun checkBoxDecrementsMatchCount() {
        val previousCountString = rule.activity.findViewById<TextView>(R.id.match_count).text.toString()
        val previousCount = if (previousCountString.isBlank()) 0 else previousCountString.toInt()

        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(
                        matches(isNotChecked())
                )
                .perform(click())
                .check(matches(isChecked()))

        onView(CoreMatchers.allOf(withId(R.id.match_count), withText((previousCount-1).toString())))
                .check(matches(isDisplayed()))
    }

    @Test
    fun checkBoxAddsToTransactionTotal() {
        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(
                        matches(isNotChecked())
                )
                .perform(click())
                .check(matches(isChecked()))

        val selectedTransaction = rule.activity.findViewById<TextView>(R.id.text_total).text.toString()
        val selectedTransactionFloat : Float = selectedTransaction.toFloat()

        val previousTransactionTotalStr = rule.activity.findViewById<TextView>(R.id.transactionAmount).text.toString()
        val previousTransactionTotal : Float = previousTransactionTotalStr.toFloat()

        var total : Float = previousTransactionTotal + selectedTransactionFloat


        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(
                        matches(isChecked())
                )
                .perform(click())
                .check(matches(isNotChecked()))

        onView(CoreMatchers.allOf(withId(R.id.transactionAmount), withText((total).toString())))
                .check(matches(isDisplayed()))
    }

    @Test
    fun checkBoxSubtractsTransactionTotal() {
        val selectedTransaction = rule.activity.findViewById<TextView>(R.id.text_total).text.toString()
        val selectedTransactionFloat : Float = selectedTransaction.toFloat()

        val previousTransactionTotalStr = rule.activity.findViewById<TextView>(R.id.transactionAmount).text.toString()
        val previousTransactionTotal : Float = previousTransactionTotalStr.toFloat()

        var total : Float = previousTransactionTotal - selectedTransactionFloat


        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(
                        matches(isNotChecked())
                )
                .perform(click())
                .check(matches(isChecked()))

        onView(CoreMatchers.allOf(withId(R.id.transactionAmount), withText((total).toString())))
                .check(matches(isDisplayed()))
    }

}