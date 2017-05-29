package com.hhd.myinstapp3;

import android.app.Activity;
import android.os.Bundle;

public class BaseMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_main);
    }
}
