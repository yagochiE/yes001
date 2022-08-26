package com.example.yes001;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmSet extends AppCompatActivity {
    AlarmManager am;
    TimePicker tp;
    Context context;
    PendingIntent pi;
    Button btnStart, btnFinish;
    ToggleButton tbtn1, tbtn2, tbtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_set);

        this.context = this;

        // findviewbyid
        tbtn1 = findViewById(R.id.tbtn1);
        tbtn2 = findViewById(R.id.tbtn2);
        tbtn4 = findViewById(R.id.tbtn4);

        // 알람매니저 설정
        am = (AlarmManager)getSystemService(ALARM_SERVICE);

        // 타임피커 설정
        tp = findViewById(R.id.tp);

        // Calendar 객체 설정
        Calendar calendar = Calendar.getInstance();

        // 알람리시버 Intent 생성
        Intent intent = new Intent(this.context, Alarm_Receiver.class);

        // 알람 시작 버튼
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calendar에 시간 셋팅
                calendar.set(Calendar.HOUR_OF_DAY, tp.getHour());
                calendar.set(Calendar.MINUTE, tp.getMinute());

                // 시간 가져옴
                int hour = tp.getHour();
                int minute = tp.getMinute();
                Toast.makeText(AlarmSet.this, "알람 예정 " + hour + "시 " + minute + "분", Toast.LENGTH_SHORT).show();

                // receiver에 string 값 넘겨주기
                intent.putExtra("state", "alarm on");

                pi = PendingIntent.getBroadcast(AlarmSet.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // 알람셋팅
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
            }
        });

        // 알람 정지 버튼
        btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AlarmSet.this, "알람 종료", Toast.LENGTH_SHORT).show();

                // 알람매니저 취소
                if(pi != null) {
                    am.cancel(pi);
                }

                intent.putExtra("state", "alarm off");

                // 알람 취소
                sendBroadcast(intent);
            }
        });

        // 토글버튼 테스트 tbtn1
        tbtn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tbtn1.setBackgroundResource(R.drawable.tbtn_test);
                if (b) {
                    tbtn1.setBackgroundResource(R.drawable.tbtn_day_shape_on);
                } else {
                    tbtn1.setBackgroundResource(R.drawable.tbtn_day_shape);
                }
            }
        });

        /*tbtn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tbtn2.setBackgroundResource(R.drawable.tbtn_day_shape_on);
                } else {
                    tbtn2.setBackgroundResource(R.drawable.tbtn_day_shape);
                }
            }
        });*/

        // 토글버튼 테스트 tbtn4
        tbtn4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //tbtn4.setBackgroundColor(Color.parseColor("#BDBDBD")); // 배경색 바꾸기
                    tbtn4.setBackgroundResource(R.drawable.tbtn_day_shape_on);
                    //Toast.makeText(context, "목요일", Toast.LENGTH_SHORT).show();
                } else {
                    // text color도 바꿔야 됨
                    //tbtn4.setBackgroundColor(Color.parseColor("#00000000"));
                    tbtn4.setBackgroundResource(R.drawable.tbtn_day_shape);
                    //tbtn4.setBackgroundResource(0); // background 속성 없애기
                }
            }
        });

    }
}
