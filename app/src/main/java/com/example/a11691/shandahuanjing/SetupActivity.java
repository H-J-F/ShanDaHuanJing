package com.example.a11691.shandahuanjing;

/**
 * Created by 11691 on 2017/3/7.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SetupActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGHT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_layout);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SetupActivity.this, MainActivity.class);
                SetupActivity.this.startActivity(mainIntent);
                SetupActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
