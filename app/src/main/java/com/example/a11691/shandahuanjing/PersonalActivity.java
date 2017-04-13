package com.example.a11691.shandahuanjing;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNoTitle();
        setContentView(R.layout.activity_personal);

        final EditText username = (EditText) findViewById(R.id.user_name);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_personal);
        final ImageView edit_name = (ImageView) findViewById(R.id.edit_name);
        final CircleImageView personal_img = (CircleImageView) findViewById(R.id.personal_image);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                linearLayout.requestFocus();
                return false;
            }
        });

        edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setEnabled(true);
                username.requestFocus();
            }
        });

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    username.setEnabled(false);
                }
            }
        });

        personal_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_dialog();
            }
        });
    }

    private void show_dialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialog_view = LayoutInflater.from(PersonalActivity.this).inflate(R.layout.head_dialog_loyout, null);
        dialog.setContentView(dialog_view);

        Button take_pic = (Button) dialog_view.findViewById(R.id.take_photo);
        Button pick_pic = (Button) dialog_view.findViewById(R.id.pick_picture);
        Button cancel = (Button) dialog_view.findViewById(R.id.head_cancel);

        take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(PersonalActivity.this, "take photo", Toast.LENGTH_SHORT).show();
            }
        });

        pick_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(PersonalActivity.this, "pick picture", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Window dialog_win = dialog.getWindow();
        dialog_win.setGravity(Gravity.BOTTOM);
        dialog_win.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void setNoTitle(){
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
}
