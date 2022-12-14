package com.example.cattower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnlogin, btnsignup;
    EditText idcat, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent service_intent = new Intent(getApplicationContext(), MyService.class);
        startService(service_intent);

        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnsignup = (Button) findViewById(R.id.btnsignup);
        idcat = (EditText) findViewById(R.id.idcat);
        pwd = (EditText) findViewById(R.id.pwd);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idcat.getText().toString();
                String pw = pwd.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("step", 1);
                intent.putExtra("mode","login,"+id+","+pw);
                startService(intent);
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("step", 1);
                intent.putExtra("mode","signup");
                startService(intent);

                Intent sign_intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(sign_intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        intent.putExtra("step", 1);
        intent.putExtra("mode","return,e");
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
            Log.d("data = ", data);

            if (data.equals("login,success")){
                Intent Menu_intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(Menu_intent);
            }
            else {
                Toast tMsg = Toast.makeText(MainActivity.this, "?????? ID/PWD??? ???????????? ????????????.", Toast.LENGTH_SHORT);
                tMsg.show();
            }
        }
    }
}