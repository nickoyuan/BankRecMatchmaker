package com.xero.interview.bankrecmatchmaker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.xero.interview.bankrecmatchmaker.R;

public class CheckedListItem extends LinearLayout implements Checkable {

    private AppCompatCheckBox checkBox;

    public CheckedListItem(Context context) {
        super(context);
        init(context);
    }

    public CheckedListItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CheckedListItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        setOrientation(HORIZONTAL);
        checkBox = (AppCompatCheckBox) layoutInflater.inflate(R.layout.list_item_checkbox, this, false);
        addView(checkBox, 0);
    }

    @Override
    public void setChecked(boolean checked) {
        checkBox.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return checkBox.isChecked();
    }

    @Override
    public void toggle() {
        checkBox.toggle();
    }

}
