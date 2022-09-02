package com.example.yes001;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timetable002.Cell;
import com.example.timetable002.Timetable_Main;

public class TimeTableTest extends AppCompatActivity {

    //private Timetable_Main timetable;
    GridLayout gridLayout;
    Button btn01, btn02, btn03, btn04, btn05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_layout_test1);

        Timetable_Main timetable = (Timetable_Main)findViewById(R.id.timetable_t);

        //timetable.addSchedule("운영체제", "1교시", "수", 2);
        timetable.addSchedule("운영체제", 2, 2, 1);
        timetable.addSchedule("DB", 3, 3, 1);

        /*timetable.addSchedule("네트워크보안", "2교시", "월", 3);
        timetable.addSchedule("어플리케이션보안", "6교시", "월", 3);
        timetable.addSchedule("모바일시스템운영", "6교시", "화", 3);
        timetable.addSchedule("캡스톤디자인(1)", "6교시", "수", 3);
        timetable.addSchedule("보안위협탐지및분석", "2교시","목", 3);
        timetable.addSchedule("자아탐색과자기개발", "6교시", "목", 3);
        timetable.addSchedule("sw테스팅", "6교시", "금", 3);*/

        gridLayout = (GridLayout)findViewById(R.id.gridlay);
        btn01 = (Button)findViewById(R.id.btn01);
        btn02 = (Button)findViewById(R.id.btn02);
        btn03 = (Button)findViewById(R.id.btn03);
        btn04 = (Button)findViewById(R.id.btn04);
        btn05 = (Button)findViewById(R.id.btn05);

        /*gridLayout.removeAllViews();

        int total = 12;
        int column = 5;
        int row = total / column;
        gridLayout.setColumnCount(row + 1);
        gridLayout.setRowCount(row+1);
        for (int i = 0, c = 0, r = 0; i < total; i++, c++) {
            if ( c == column) {
                c = 0;
                r++;
            }
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.width = GridLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.rightMargin = 5;
            layoutParams.topMargin = 5;
            layoutParams.setGravity(Gravity.CENTER);
            layoutParams.columnSpec = GridLayout.spec(c);
            layoutParams.rowSpec = GridLayout.spec(r);
            gridLayout.setBackgroundColor(Color.parseColor("#ff00ff"));

        }*/

        gridLayout.setColumnCount(6);
        gridLayout.setRowCount(4);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();


    }

    private void init() {
        //this.context = this;
    }


    public void setPointLocation(Point p) {
        //int row = Integer.parseInt(p.getPosition());
    }
}
