package com.example.yes001;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmSet extends AppCompatActivity {
    AlarmManager am;
    TimePicker tp;
    Context context;
    PendingIntent pi;
    Button btnStart, btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_set);

        this.context = this;

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
    }
}
