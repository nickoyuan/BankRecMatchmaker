package com.xero.interview.bankrecmatchmaker

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
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

    @Test
    fun tappingCheckBox() {
        if (getRVcount() > 0) {
            onView(withId(R.id.recycler_view))
                    .perform(
                            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                                    0,
                                    click()
                            )
                    )
        }
    }

}