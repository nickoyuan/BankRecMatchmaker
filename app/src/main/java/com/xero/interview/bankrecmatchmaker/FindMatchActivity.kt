package com.xero.interview.bankrecmatchmaker

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_find_match.*
import java.util.ArrayList

class FindMatchActivity : AppCompatActivity() {

    private lateinit var viewModel: MatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_match)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val matchText = findViewById<TextView>(R.id.match_count)
        val transactionTotalText = findViewById<TextView>(R.id.transactionAmount)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val autoAdd = findViewById<Button>(R.id.btnAutoAdd)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.title_find_match)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val items = buildMockData()

        viewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)
        viewModel.viewState.observe(this, android.arch.lifecycle.Observer {
            it -> it?.let { render(it) }

        })
        viewModel.repository = Repository(this)
        viewModel.initialize()
        viewModel.reset()

        matchText.text = viewModel.repository.getMatchCount().toString()
        transactionTotalText.text = viewModel.repository.getTransactionTotal().toString()

        val adapter = MatchAdapter(items, viewModel)
        recyclerView.adapter = adapter

      /*  autoAdd.setOnClickListener {
            viewModel.findSubsetOfTransactionSum(buildMockData())
        }*/
    }


    private fun render(uiModel: MatchItemUIModel) {
        when (uiModel) {
            is MatchItemUIModel.TransactionAmountUpdated -> {
                transactionAmount.text = uiModel.amount.toString()
            }
            is MatchItemUIModel.MatchCountUpdated -> {
                match_count.text = uiModel.matchCount.toString()
            }
        }
    }

    private fun buildMockData(): List<MatchItem> {
        val items = ArrayList<MatchItem>()
        items.add(MatchItem("City Limousines", "30 Aug", 249.00f, "Sales Invoice"))
        items.add(MatchItem("Ridgeway University", "12 Sep", 618.50f, "Sales Invoice"))
        items.add(MatchItem("Cube Land", "22 Sep", 495.00f, "Sales Invoice"))
        items.add(MatchItem("Bayside Club", "23 Sep", 234.00f, "Sales Invoice"))
        items.add(MatchItem("SMART Agency", "12 Sep", 250f, "Sales Invoice"))
        items.add(MatchItem("PowerDirect", "11 Sep", 108.60f, "Sales Invoice"))
        items.add(MatchItem("PC Complete", "17 Sep", 216.99f, "Sales Invoice"))
        items.add(MatchItem("Truxton Properties", "17 Sep", 181.25f, "Sales Invoice"))
        items.add(MatchItem("MCO Cleaning Services", "17 Sep", 170.50f, "Sales Invoice"))
        items.add(MatchItem("Gateway Motors", "18 Sep", 411.35f, "Sales Invoice"))
        return items
    }

    companion object {

        val TARGET_MATCH_VALUE = "com.xero.interview.target_match_value"
    }

}
