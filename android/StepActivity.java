package com.example.cattower;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class StepActivity extends Activity {
    int st1, st2 = 0;
    SeekBar step1, step2;
    TextView steptext;
    Button btnauto;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        step1 = (SeekBar) findViewById(R.id.step1);
        step2 = (SeekBar) findViewById(R.id.step2);
        steptext = (TextView) findViewById(R.id.steptext);
        btnauto = (Button) findViewById(R.id.btnauto);

        step1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                st1 = step1.getProgress();

                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("step", 1);
                intent.putExtra("mode","step,"+st1+","+st2);
                startService(intent);
            }
        });

        step2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                st2 = step2.getProgress();

                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("step", 1);
                intent.putExtra("mode","step,"+st1+","+st2);
                startService(intent);
            }
        });

        btnauto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("step", 1);
                intent.putExtra("mode","step,auto");
                startService(intent);
            }
        });
    }

    @Override
    // ?????????????????? ????????? ???????????? ???????????? ???????????????. processCommand??? ?????????????????? ?????????
    // ???????????? oncreate?????? ?????? ?????? ????????? ?????????(????????? ????????????) oncreate?????? ???????????? ??????
    // onNewIntent() ??? ?????? ?????? ??????. ????????? -> ?????????????????? ??????????????????.
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent){
        int step = intent.getIntExtra("step",0);
        Log.d("TAG","recv step ??????");
        if(step==3){
            Log.d("TAG","recv data ??????");
            String data = intent.getStringExtra("data");
            Log.d("data = ", data);

            String[] array = data.split(",");

            steptext.setText(data);
            step1.setProgress(Integer.parseInt(array[1]));
            step2.setProgress(Integer.parseInt(array[2]));

            if (array.length==3){
                steptext.setText("[??????] ?????? 1, 2 : "+array[1]+","+array[2]);
            }
            else{
                steptext.setText("[??????] ?????? 1, 2 : "+array[1]+","+array[2]);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        intent.putExtra("step", 1);
        intent.putExtra("mode","return,m");
        startService(intent);
        super.onBackPressed();
    }
}