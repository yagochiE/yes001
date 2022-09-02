package com.example.timetable002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Timetable_Main extends GridLayout {

    private static String TAG = "Timetable001";
    private ArrayList<TextView> cells = new ArrayList<>();

    private String[] row_names, column_names;

    protected int cellMarginTop, cellMarginBottom, cellMarginLeft, cellMarginRight;
    private int cellTextColor;
    private int cellColor; //cell 배경색

    public Timetable_Main(Context context) {
        super(context);
    }

    public Timetable_Main(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Timetable_Main(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Timetable_Main(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.timetable_layout, this);

        if (attrs != null) {
            //attrs.xml에 정의한 스타일 가져오기
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Timetable_Main);
            cellColor = a.getColor(R.styleable.Timetable_Main_cellColor, getResources().getColor(R.color.white, null));
            cellTextColor = a.getColor(R.styleable.Timetable_Main_cellTextColor, getResources().getColor(R.color.black, null));
            cellMarginTop = a.getInt(R.styleable.Timetable_Main_cellMarginTop, 5);
            cellMarginBottom = a.getInt(R.styleable.Timetable_Main_cellMarginBottom, 5);
            cellMarginRight = a.getInt(R.styleable.Timetable_Main_cellMarginRight, 5);
            cellMarginLeft = a.getInt(R.styleable.Timetable_Main_cellMarginLeft, 5);
            a.recycle();; // 이용이 끝났으면 recycle() 호출
        }

        removeAllViews();
        createInitCells();
    }

    //초기 cell들 생성
    private void createInitCells() {
        initRowColumnNames(); //행, 열 이름 초기화
        addCells(); //cell 추가
    }

    //row, column 이름 초기화
    private void initRowColumnNames() {
        row_names = new String[getRowCount()];
        column_names = new String[getColumnCount()];

        for(int i = 0; i < getRowCount(); i++)
            row_names[i] = i+"";
        for(int i = 0; i < getColumnCount(); i++)
            column_names[i] = i+"";
    }

    //row, column만큼 cell 추가
    private void addCells() {
        for(int i = 0; i < getRowCount(); i++) {
            for(int j = 0; j < getColumnCount(); j++) {
                Cell cell = new Cell(getContext());
                cell.setTag(row_names[i]+"-"+column_names[j]); //행열로 태그 설정

                cell.setRow(i);
                cell.setColumn(j);

                cell.setBackgroundColor(cellColor); //cell 배경색 설정
                cell.setTextColor(cellTextColor); //cell text 색 설정
                cell.setGravity(Gravity.CENTER);
                cell.setText("");

                if(i==0 && j!=0)
                    cell.setText(column_names[j]);
                if(j==0 && i!=0)
                    cell.setText(row_names[i]);

                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(i, 1.0f), GridLayout.spec(j, 1.0f));
                layoutParams.setGravity(Gravity.FILL);
                layoutParams.width = 0; //스케줄 추가해도 너비 일정하게 유지
                layoutParams.height = LayoutParams.WRAP_CONTENT;
                layoutParams.setMargins(cellMarginLeft, cellMarginTop, cellMarginRight, cellMarginBottom);
                cell.setLayoutParams(layoutParams);

                addView(cell);
            }
        }
    }

    //스케줄 추가
    public void addSchedule(String text, String row_name, String column_name, int blocks) {
        //스케줄 추가 cell
        Cell schedule_cell = findCell(row_name, column_name);

        if(schedule_cell.isScheduled()) {
            Toast.makeText(getContext(), "already exists schedule", Toast.LENGTH_SHORT).show();
            return;
        }

        /*if(schedule_cell.getVisibility() == View.GONE || schedule_cell.isScheduled()) { //스케줄 있는 시간
            Toast.makeText(getContext(), "already exists schedule", Toast.LENGTH_SHORT).show();
            return;
        }*/

        ArrayList<String> row_list = new ArrayList<>(Arrays.asList(row_names));
        int origin_index = row_list.indexOf(row_name);

        //cell 삭제
        for(int i=1; i<blocks; i++) {
            Cell cell = findCell(row_names[origin_index+i], column_name);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        schedule_cell.setText(text);
        schedule_cell.setScheduled(true);
        schedule_cell.setClickable(true);
        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(origin_index, blocks, 1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    //스케줄 추가
    public void addSchedule(String text, String row_name, String column_name, int blocks, int backgroundColor)
    {
        //스케줄 추가 cell
        Cell schedule_cell = findCell(row_name,column_name);

        if(schedule_cell.getVisibility() == View.GONE || schedule_cell.isScheduled())//스케줄 있는 시간
        {
            Toast.makeText(getContext(),"already exists schedule",Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> row_list = new ArrayList<>(Arrays.asList(row_names));
        int origin_index = row_list.indexOf(row_name);

        //삭제 cell
        for(int i=1;i<blocks;i++)
        {
            Cell cell= findCell(row_names[origin_index+i],column_name);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        //스케줄 추가 cell
        schedule_cell.setText(text);
        schedule_cell.setScheduled(true);
        schedule_cell.setClickable(true);
        //cell 색깔 지정
        schedule_cell.setBackgroundColor(backgroundColor);

        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(origin_index, blocks,1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    //스케줄 추가
    public void addSchedule(String text, String row_name, String column_name, int blocks, int backgroundColor, int textColor)
    {
        //스케줄 추가 cell
        Cell schedule_cell = findCell(row_name,column_name);

        if(schedule_cell.getVisibility() == View.GONE || schedule_cell.isScheduled())//스케줄 있는 시간
        {
            Toast.makeText(getContext(),"already exists schedule",Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> row_list = new ArrayList<>(Arrays.asList(row_names));
        int origin_index = row_list.indexOf(row_name);

        //삭제 cell
        for(int i=1;i<blocks;i++)
        {
            Cell cell= findCell(row_names[origin_index+i],column_name);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        //스케줄 추가 cell
        schedule_cell.setText(text);
        schedule_cell.setScheduled(true);
        schedule_cell.setClickable(true);
        //색지정
        schedule_cell.setBackgroundColor(backgroundColor);
        schedule_cell.setTextColor(textColor);

        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(origin_index, blocks,1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    //스케줄 추가
    public void addSchedule(String text, int row, int column, int blocks)
    {
        //스케줄 추가 cell
        Cell schedule_cell = findCell(row,column);

        if(schedule_cell.getVisibility() == View.GONE || schedule_cell.isScheduled())//스케줄 있는 시간
        {
            Toast.makeText(getContext(),"already exists schedule",Toast.LENGTH_SHORT).show();
            return;
        }
        //cell 삭제
        for(int i=1;i<blocks;i++)
        {
            Cell cell= findCell(row + i,column);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        //스케줄 추가 cell
        schedule_cell.setText(text);
        schedule_cell.setScheduled(true);
        schedule_cell.setClickable(true);
        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, blocks,1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    //스케줄 추가
    public void addSchedule(String text, int row, int column, int blocks, int backgroundColor)
    {
        //스케줄 추가 cell
        Cell schedule_cell = findCell(row,column);

        if(schedule_cell.getVisibility() == View.GONE || schedule_cell.isScheduled())//스케줄 있는 시간
        {
            Toast.makeText(getContext(),"already exists schedule",Toast.LENGTH_SHORT).show();
            return;
        }

        //cell 삭제
        for(int i=1;i<blocks;i++)
        {
            Cell cell= findCell(row + i,column);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        //스케줄 추가 cell
        schedule_cell.setText(text);
        schedule_cell.setScheduled(true);
        schedule_cell.setClickable(true);
        //cell 색깔 지정
        schedule_cell.setBackgroundColor(backgroundColor);

        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, blocks,1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    //스케줄 추가
    public void addSchedule(String text, int row, int column, int blocks, int backgroundColor, int textColor)
    {
        //스케줄 추가 cell
        Cell schedule_cell = findCell(row,column);

        if(schedule_cell.getVisibility() == View.GONE || schedule_cell.isScheduled())//스케줄 있는 시간
        {
            Toast.makeText(getContext(),"already exists schedule",Toast.LENGTH_SHORT).show();
            return;
        }

        //cell 삭제
        for(int i=1;i<blocks;i++)
        {
            Cell cell= findCell(row + i,column);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        //스케줄 추가 cell
        schedule_cell.setText(text);
        schedule_cell.setScheduled(true);
        schedule_cell.setClickable(true);
        //색지정
        schedule_cell.setBackgroundColor(backgroundColor);
        schedule_cell.setTextColor(textColor);

        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, blocks,1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    //스케줄 삭제
    public void deleteSchedule(String row_name, String column_name) {
        Cell schedule_cell = findCell(row_name, column_name);

        int row = schedule_cell.getRow();

        if(!schedule_cell.isScheduled()) { //스케줄 없는 경우
            Toast.makeText(getContext(), "not exists any schedule", Toast.LENGTH_SHORT).show();
            return;
        }

        //스케줄 삭제처리
        schedule_cell.setText("");
        schedule_cell.setScheduled(false); //스케줄 삭제됨
        schedule_cell.setClickable(false);
        //색 복구
        schedule_cell.setBackgroundColor(cellColor);
        schedule_cell.setTextColor(cellTextColor);

        //병합 복구
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, 1, 1.0f);
        schedule_cell.setLayoutParams(layoutParams);

        //지워진 cell들 원래대로
        ArrayList<Cell> spannedCells = schedule_cell.getSpannedCells();

        for(int i = 0; i < spannedCells.size(); i++) {
            spannedCells.get(i).setVisibility(View.VISIBLE);
        }
        spannedCells.clear();
    }

    public void deleteSchedule(int row, int column)
    {
        Cell schedule_cell = findCell(row,column);

        if(!schedule_cell.isScheduled())//스케줄 없는경우
        {
            Toast.makeText(getContext(),"not exists any schedule",Toast.LENGTH_SHORT).show();
            return;
        }

        //스케줄 삭제처리
        schedule_cell.setText("");
        schedule_cell.setScheduled(false);//스케줄 삭제됨
        schedule_cell.setClickable(false);
        //색 복구
        schedule_cell.setBackgroundColor(cellColor);
        schedule_cell.setTextColor(cellTextColor);

        //병합 복구
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, 1,1.0f);
        schedule_cell.setLayoutParams(layoutParams);

        //지워진 cell들 원래대로
        ArrayList<Cell> spannedCells = schedule_cell.getSpannedCells();

        for(int i=0;i<spannedCells.size();i++)
        {
            spannedCells.get(i).setVisibility(View.VISIBLE);
        }
        spannedCells.clear();

    }

    public void deleteScheduleWithText(String text)
    {
        Cell schedule_cell = findCellWithText(text);

        int row = schedule_cell.getRow();

        if(!schedule_cell.isScheduled())//스케줄 없는경우
        {
            Toast.makeText(getContext(),"not exists any schedule",Toast.LENGTH_SHORT).show();
            return;
        }

        //스케줄 삭제처리
        schedule_cell.setText("");
        schedule_cell.setScheduled(false);//스케줄 삭제됨
        schedule_cell.setClickable(false);
        //색 복구
        schedule_cell.setBackgroundColor(cellColor);
        schedule_cell.setTextColor(cellTextColor);

        //병합 복구
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, 1,1.0f);
        schedule_cell.setLayoutParams(layoutParams);

        //지워진 cell들 원래대로
        ArrayList<Cell> spannedCells = schedule_cell.getSpannedCells();

        for(int i=0;i<spannedCells.size();i++)
        {
            spannedCells.get(i).setVisibility(View.VISIBLE);
        }
        spannedCells.clear();
    }

    //스케줄 수정
    public void modifySchedule(String row_name, String column_name, String change_text, int blocks) {
        Cell schedule_cell = findCell(row_name, column_name);

        int row = schedule_cell.getRow();
        int column = schedule_cell.getColumn();

        if(!schedule_cell.isScheduled()) { //스케줄 없는 경우
            Toast.makeText(getContext(), "not exists any schedule", Toast.LENGTH_SHORT).show();
            return;
        }

        schedule_cell.setText(change_text);

        ArrayList<Cell> spannedCells = schedule_cell.getSpannedCells();
        for(int i = 0; i < spannedCells.size(); i++) {
            spannedCells.get(i).setVisibility(View.VISIBLE);
        }
        spannedCells.clear();

        //cell 삭제
        for(int i = 1; i < blocks; i++) {
            Cell cell = findCell(row + i, column);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, blocks, 1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    public void modifySchedule(int row, int column,String change_text,int blocks)
    {
        Cell schedule_cell = findCell(row, column);

        if(!schedule_cell.isScheduled())//스케줄 없는경우
        {
            Toast.makeText(getContext(),"not exists any schedule",Toast.LENGTH_SHORT).show();
            return;
        }

        schedule_cell.setText(change_text);

        ArrayList<Cell> spannedCells = schedule_cell.getSpannedCells();
        for(int i=0;i<spannedCells.size();i++)
        {
            spannedCells.get(i).setVisibility(View.VISIBLE);
        }
        spannedCells.clear();

        //cell 삭제
        for(int i=1;i<blocks;i++)
        {
            Cell cell= findCell(row + i,column);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, blocks,1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    public void modifySchedule(int row, int column,String change_text,int blocks, int backgroundColor, int textColor)
    {
        Cell schedule_cell = findCell(row, column);

        if(!schedule_cell.isScheduled())//스케줄 없는경우
        {
            Toast.makeText(getContext(),"not exists any schedule",Toast.LENGTH_SHORT).show();
            return;
        }

        schedule_cell.setText(change_text);
        schedule_cell.setBackgroundColor(backgroundColor);
        schedule_cell.setTextColor(textColor);

        ArrayList<Cell> spannedCells = schedule_cell.getSpannedCells();
        for(int i=0;i<spannedCells.size();i++)
        {
            spannedCells.get(i).setVisibility(View.VISIBLE);
        }
        spannedCells.clear();

        //cell 삭제
        for(int i=1;i<blocks;i++)
        {
            Cell cell= findCell(row + i,column);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, blocks,1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    public void modifySchedule(String row_name, String column_name, String change_text, int blocks, int backgroundColor, int textColor)
    {
        Cell schedule_cell = findCell(row_name, column_name);

        int row = schedule_cell.getRow();
        int column = schedule_cell.getColumn();

        if(!schedule_cell.isScheduled())//스케줄 없는경우
        {
            Toast.makeText(getContext(),"not exists any schedule",Toast.LENGTH_SHORT).show();
            return;
        }

        schedule_cell.setText(change_text);
        schedule_cell.setBackgroundColor(backgroundColor);
        schedule_cell.setTextColor(textColor);

        ArrayList<Cell> spannedCells = schedule_cell.getSpannedCells();
        for(int i=0;i<spannedCells.size();i++)
        {
            spannedCells.get(i).setVisibility(View.VISIBLE);
        }
        spannedCells.clear();

        //cell 삭제
        for(int i=1;i<blocks;i++)
        {
            Cell cell= findCell(row + i,column);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, blocks,1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    public void modifyScheduleWithText(String origin_text, String change_text, int blocks)
    {
        Cell schedule_cell = findCellWithText(origin_text);

        int row = schedule_cell.getRow();
        int column = schedule_cell.getColumn();

        if(!schedule_cell.isScheduled())//스케줄 없는경우
        {
            Toast.makeText(getContext(),"not exists any schedule",Toast.LENGTH_SHORT).show();
            return;
        }

        schedule_cell.setText(change_text);

        ArrayList<Cell> spannedCells = schedule_cell.getSpannedCells();
        for(int i=0;i<spannedCells.size();i++)
        {
            spannedCells.get(i).setVisibility(View.VISIBLE);
        }
        spannedCells.clear();

        //cell 삭제
        for(int i=1;i<blocks;i++)
        {
            Cell cell= findCell(row + i,column);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, blocks,1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    public void modifyScheduleWithText(String origin_text, String change_text, int blocks, int backgroundColor, int textColor)
    {
        Cell schedule_cell = findCellWithText(origin_text);

        int row = schedule_cell.getRow();
        int column = schedule_cell.getColumn();

        if(!schedule_cell.isScheduled())//스케줄 없는경우
        {
            Toast.makeText(getContext(),"not exists any schedule",Toast.LENGTH_SHORT).show();
            return;
        }

        schedule_cell.setText(change_text);
        schedule_cell.setBackgroundColor(backgroundColor);
        schedule_cell.setTextColor(textColor);

        ArrayList<Cell> spannedCells = schedule_cell.getSpannedCells();
        for(int i=0;i<spannedCells.size();i++)
        {
            spannedCells.get(i).setVisibility(View.VISIBLE);
        }
        spannedCells.clear();

        //cell 삭제
        for(int i=1;i<blocks;i++)
        {
            Cell cell= findCell(row + i,column);
            cell.setVisibility(View.GONE);

            schedule_cell.addSpannedCells(cell);
        }

        //병합
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)schedule_cell.getLayoutParams();
        layoutParams.rowSpec = GridLayout.spec(row, blocks,1.0f);
        schedule_cell.setLayoutParams(layoutParams);
    }

    //모든 cell 가져옴
    public Cell[][] getAllCell() {
        Cell[][] list = new Cell[getRowCount()][getColumnCount()];

        for(int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                list[i][j] = findCell(row_names[i], column_names[j]);
            }
        }
        return list;
    }

    public int getCellTextColor() {
        return cellTextColor;
    }

    public void setCellTextColor(int cellTextColor) {
        this.cellTextColor = cellTextColor;

        for(int i=0;i<getRowCount();i++)
        {
            for(int j=0;j<getColumnCount();j++)
            {
                Cell cell = (Cell)findViewWithTag(row_names[i]+"-"+column_names[j]);
                cell.setTextColor(cellTextColor);
            }
        }
    }

    public int getCellColor() {
        return cellColor;
    }

    //cell 배경 색
    public void setCellColor(int cellColor) {
        this.cellColor = cellColor;

        for(int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                Cell cell = (Cell) findViewWithTag(row_names[i]+"-"+column_names[j]);
                cell.setBackgroundColor(cellColor);
            }
        }
    }

    //처음 세로줄
    public void setRowNames(String[] row_names)
    {
        for(int i=0;i<getRowCount();i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                Cell cell = (Cell) findViewWithTag(this.row_names[i] + "-" + this.column_names[j]);
                cell.setTag(row_names[i] + "-" + this.column_names[j]);

                if(j==0 && i!=0)//첫줄
                    cell.setText(row_names[i]);
            }
        }

        this.row_names = row_names.clone();

    }

    //처음 가로줄
    public void setColumnNames(String[] column_names)
    {
        for(int i=0;i<getRowCount();i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                Cell cell = (Cell) findViewWithTag(this.row_names[i] + "-" + this.column_names[j]);
                cell.setTag(this.row_names[i] + "-" + column_names[j]);

                if(i==0 && j!=0)//첫줄
                    cell.setText(column_names[j]);
            }
        }

        this.column_names = column_names;
    }

    // 삭제하세여~~~~~~~~~~~~~~~~~~~~~~~
    public int sum(int q, int w) { return q+w; }


    //마진 값
    public void setCellsMargin(int left, int top, int right, int bottom) {
        cellMarginLeft = left;
        cellMarginRight = right;
        cellMarginTop = top;
        cellMarginBottom = bottom;

        for(int i = 0; i < getRowCount(); i++) {
            for(int j = 0; j < getColumnCount(); j++) {
                Cell cell = (Cell)findViewWithTag(row_names[i]+"-"+column_names[j]);
                GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams)cell.getLayoutParams();

                layoutParams.setMargins(left, top, right, bottom);
                cell.setLayoutParams(layoutParams);
            }
        }
    }

    //특정 row, column의 cell 변환
    public Cell findCell(String row_name, String column_name) {
        Cell cell = (Cell)findViewWithTag(row_name+"-"+column_name);
        return cell;
    }

    //특정 row, column의 cell 반환
    public Cell findCell(int row, int column) {
        Cell cell = (Cell)findViewWithTag(row_names[row-1]+"-"+column_names[column-1]);
        return cell;
    }

    //해당 cell의 text를 보고 찾음
    public Cell findCellWithText(String text) {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                Cell cell = (Cell)findViewWithTag(row_names[i]+"-"+column_names[j]);
                if(cell.getText().equals(text))
                    return cell;
            }
        }
        return null;
    }

    @Override
    public void setRowCount(int rowCount) {
        removeAllViews();

        super.setRowCount(rowCount);

        initRowColumnNames();
        addCells();
    }

    @Override
    public void setColumnCount(int columnCount) {
        removeAllViews();

        super.setColumnCount(columnCount);

        initRowColumnNames();
        addCells();
    }
}