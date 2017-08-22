package com.tuhocandroid.navdrawerandtablayout.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tuhocandroid.navdrawerandtablayout.R;

public class MainActivity extends AppCompatActivity {


private final int WAIT_TIME = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(MainActivity.this,Zing.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        },WAIT_TIME);
    }
    }
