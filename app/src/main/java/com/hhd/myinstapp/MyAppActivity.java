package com.hhd.myinstapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MyAppActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("this is myappactivity");
        setContentView(textView);
//        setContentView(R.layout.activity_hello);
    }
}
