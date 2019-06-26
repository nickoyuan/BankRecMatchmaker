package com.xero.interview.bankrecmatchmaker

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import org.junit.Rule
import org.junit.Test


@LargeTest
class FindMatchActivityTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(FindMatchActivity::class.java)

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
    fun unCheckBoxDecrementsTotalCountView() {


    }

}