package com.example.timetable002;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Cell extends FrameLayout {

    private int row, column;
    private boolean isScheduled = false;
    private ArrayList<Cell> spannedCells = new ArrayList<>();
    private TextView textView;

    public Cell(Context context) {
        super(context);
        initView(context, null);
    }

    public Cell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public Cell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        // 초기화 시 할 작업들
        textView = new TextView(context);

        //ripple effect 적용
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        int resld = typedValue.resourceId;
        textView.setBackgroundResource(resld);

        setClickable(false);
        addView(textView);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)textView.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        textView.setLayoutParams(layoutParams);
    }

    public int getRow() { return row; }

    public void setRow(int row) { this.row = row; }

    public int getColumn() { return column; }

    public void setColumn(int column) { this.column = column; }

    //병합되어서 지워진 cell 추가
    public void addSpannedCells(Cell cell) { spannedCells.add(cell); }

    public void removeSpannedCells(int index) {
        spannedCells.get(index).setVisibility(View.VISIBLE);
        spannedCells.remove(index);
    }

    public void removeSpannedCells(Object o) {
        spannedCells.get(spannedCells.indexOf(o)).setVisibility(View.VISIBLE);
        spannedCells.remove(o);
    }

    public ArrayList<Cell> getSpannedCells() { return spannedCells; }

    public boolean isScheduled() { return isScheduled; }
    public void setScheduled(boolean isScheduled) { this.isScheduled = isScheduled; }
    public TextView getTextView() { return textView; }

    public void setGravity(int gravity) { textView.setGravity(gravity); }

    public void setTextColor(int color) { textView.setTextColor(color); }

    public void setText(String text) { textView.setText(text); }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        textView.setClickable(clickable);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        textView.setVisibility(visibility);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        setClickable(true);
        super.setOnClickListener(l);
    }

    public String getText() { return textView.getText().toString(); }

    /*public Cell(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    } //기본 생성자 만들 때 같이 만들어 졌는데 필요 없어보여서 주석 처리*/
}
