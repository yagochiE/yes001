package com.example.yes001;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

public class Alarm_Receiver extends BroadcastReceiver {
    Context context;

    public Alarm_Receiver() {
        super();
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        // intent로부터 전달받은 string
        String get_yout_string = intent.getExtras().getString("state");

        // RingtonePlayingService 서비스 intent 생성
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        // RingtonePlayingService로 extra string 값 보내기
        service_intent.putExtra("state", get_yout_string);

        // start the ringtone service

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            this.context.startForegroundService((service_intent));
        } else {
            this.context.startService(service_intent);
        }
    }
}
