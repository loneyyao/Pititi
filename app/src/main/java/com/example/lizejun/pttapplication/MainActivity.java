package com.example.lizejun.pttapplication;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View start = findViewById(R.id.start);
        View stop = findViewById(R.id.stop);
        final Chronometer ptt_speaking_duration = findViewById(R.id.ptt_speaking_duration);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptt_speaking_duration.setBase(SystemClock.elapsedRealtime());//计时器清零
                int hour = (int) ((SystemClock.elapsedRealtime() - ptt_speaking_duration.getBase()) / 1000 / 60);
                ptt_speaking_duration.setFormat("0"+String.valueOf(hour)+":%s");
                ptt_speaking_duration.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ptt_speaking_duration.stop();
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });

    }
}
