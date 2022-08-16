package com.example.yes001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv1, tv2;
    Button btn1, btnAdd;
    int day, test1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        btn1 = findViewById(R.id.btn1);
        btnAdd = findViewById(R.id.btnAdd);

        tv1.setText("헬로 월드~~~");
        tv1.setText("test5");

        day = 0; test1 = 0;
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (day) {
                    case 0 : tv2.setText("월요일");
                        day++;
                        break;
                    case 1 : tv2.setText("화요일");
                        day++;
                        break;
                    case 2 : tv2.setText("수요일");
                        day++;
                        break;
                    case 3 : tv2.setText("목요일");
                        day++;
                        break;
                    case 4 : tv2.setText("금요일");
                        day++;
                        break;
                    case 5 : tv2.setText("토요일");
                        day++;
                        break;
                    case 6 : tv2.setText("일요일");
                        day = 0;
                        test1 = 1;
                        break;
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Test.class);
                startActivity(intent);
            }
        });
    }
}