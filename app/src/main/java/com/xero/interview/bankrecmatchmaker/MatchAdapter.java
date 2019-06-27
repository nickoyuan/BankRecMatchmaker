package com.xero.interview.bankrecmatchmaker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private List<MatchItem> matchItems;
    private MatchViewModel viewModel;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mainText;
        private TextView total;
        private TextView subtextLeft;
        private TextView subtextRight;

        // matchItems are items in the list
        private List<MatchItem> matchItems;

        //View Model to update
        private MatchViewModel viewModel;


        public ViewHolder(View itemView, MatchViewModel viewModel, List<MatchItem> matchItems) {
            super(itemView);
            mainText = itemView.findViewById(R.id.text_main);
            total = itemView.findViewById(R.id.text_total);
            subtextLeft = itemView.findViewById(R.id.text_sub_left);
            subtextRight = itemView.findViewById(R.id.text_sub_right);
            this.matchItems =  matchItems;
            this.viewModel = viewModel;
            itemView.setOnClickListener(this);
        }

        public void bind(MatchItem matchItem) {
            mainText.setText(matchItem.getPaidTo());
            total.setText(Float.toString(matchItem.getTotal()));
            subtextLeft.setText(matchItem.getTransactionDate());
            subtextRight.setText(matchItem.getDocType());
        }

        @Override
        public void onClick(View v) {
            toggleCheckBox((CheckedListItem) v);
            updateMatchAndTransaction(
                isCheckedState((CheckedListItem) v),
                getAdapterPosition()
            );
        }

        public void updateMatchAndTransaction(boolean isChecked, int adapterPosition) {
            if(isChecked) {
                viewModel.decrementMatchCount();
                viewModel.subtractFromTransactionAmount(
                    matchItems.get(adapterPosition).getTotal()
                );
            } else {
                viewModel.incrementMatchCount();
                viewModel.addToTransactionAmount(
                    matchItems.get(adapterPosition).getTotal()
                );
            }
        }

        public Boolean isCheckedState(CheckedListItem checkedListItem) {
            return checkedListItem.isChecked();
        }

        public void toggleCheckBox(CheckedListItem checkedListItem) {
            checkedListItem.toggle();
        }

    }

    public MatchAdapter(List<MatchItem> matchItems, MatchViewModel viewModel) {
        this.matchItems = matchItems;
        this.viewModel = viewModel;
    }

    /**
     *  Views are created and the ViewHolder contains references to them so that the data can be set quickly
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CheckedListItem listItem = (CheckedListItem) layoutInflater.inflate(
            R.layout.list_item_match,
            parent,
            false
        );
        return new ViewHolder(listItem, viewModel, matchItems);
    }

    /**
     * the specific data is assigned to the Views
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MatchItem matchItem = matchItems.get(position);
        holder.bind(matchItem);
    }

    @Override
    public int getItemCount() {
        return matchItems.size();
    }

}
