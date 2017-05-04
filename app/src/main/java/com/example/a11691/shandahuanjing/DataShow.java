package com.example.a11691.shandahuanjing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DataShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_show_layout);

        TextView any_data = (TextView) findViewById(R.id.any_data);

        Bundle get_data_bundle = this.getIntent().getExtras();
        String title = get_data_bundle.getString("title");
        String data = get_data_bundle.getString("value");
        this.setTitle(title);
        any_data.setText(data);
    }
}
