package com.example.cattower;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HeatActivity extends Activity {
    Button btnheatch;
    TextView temp_data, temp_data1;
    ImageView tempImage;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat);

        btnheatch = (Button) findViewById(R.id.btnheatch);
        temp_data = (TextView) findViewById(R.id.temp_data);
        temp_data1 = (TextView) findViewById(R.id.temp_data1);
        tempImage = (ImageView) findViewById(R.id.tempImage);

        btnheatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnheatch.getText().equals("ON")) {
                    Intent intent = new Intent(getApplicationContext(), MyService.class);
                    intent.putExtra("step", 1);
                    intent.putExtra("mode","temp,off");
                    startService(intent);
                }
                else if (btnheatch.getText().equals("OFF")) {
                    Intent intent = new Intent(getApplicationContext(), MyService.class);
                    intent.putExtra("step", 1);
                    intent.putExtra("mode","temp,auto");
                    startService(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MyService.class);
                    intent.putExtra("step", 1);
                    intent.putExtra("mode","temp,on");
                    startService(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        intent.putExtra("step", 1);
        intent.putExtra("mode","return,m");
        startService(intent);
        super.onBackPressed();
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
            Log.d("mytag","data = "+ data);

            String[] array = data.split(",");

            temp_data.setText("?????? ?????? : "+array[1]+"??????");


            if (array[2].equals("low")){
                array[2] = "????????????";
                tempImage.setImageResource(R.drawable.cold);
            } else if (array[2].equals("good")){
                array[2] = "????????????";
                tempImage.setImageResource(R.drawable.good);
            } else if (array[2].equals("high")){
                array[2] = "????????????";
                tempImage.setImageResource(R.drawable.hot);
            }

            temp_data1.setText(array[2]+"?????????.");
            btnheatch.setText(array[3]);
        }
    }
}
