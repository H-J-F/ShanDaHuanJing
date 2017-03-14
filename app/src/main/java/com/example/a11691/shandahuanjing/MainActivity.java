package com.example.a11691.shandahuanjing;

import android.app.Dialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnPageChangeListener{
    private DrawerLayout main_drawerlayout;
    private View view1, view2, view3;
    private List<View> viewList;
    private ViewPager viewPager;
    private String dialog_items[] = {"CD座", "EF座", "至诚"};
    public boolean follow_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);
        main_drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }

        Button dialog_btn = (Button) findViewById(R.id.address_btn);
        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        LayoutInflater inflater=getLayoutInflater();
        view1 = inflater.inflate(R.layout.viewpager_layout, null);
        view2 = inflater.inflate(R.layout.viewpager_layout, null);
        view3 = inflater.inflate(R.layout.viewpager_layout, null);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                main_drawerlayout.closeDrawers();
                return true;
            }
        });
    }

    private void showDialog(){
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialog_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layout, null);
        dialog.setContentView(dialog_view);

        TextView dia_CD = (TextView) dialog_view.findViewById(R.id.dialog_CD);
        TextView dia_EF = (TextView) dialog_view.findViewById(R.id.dialog_EF);
        TextView dia_ZC = (TextView) dialog_view.findViewById(R.id.dialog_zhicheng);

        List<TextView> TV_List = new ArrayList<>();
        TV_List.add(dia_CD);
        TV_List.add(dia_EF);
        TV_List.add(dia_ZC);

        for (int i = 0; i < TV_List.size(); i++) {
            final int index = i;
            TV_List.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager = (ViewPager) findViewById(R.id.viewpager);
                    Button dialog_btn = (Button) findViewById(R.id.address_btn);
                    dialog_btn.setText(dialog_items[index]);
                    dialog.dismiss();
                    viewPager.setCurrentItem(index, true);

                }
            });
        }

        Window dialog_win = dialog.getWindow();
        dialog_win.setGravity(Gravity.BOTTOM);
        dialog_win.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public boolean onCreateOptionsMenu(Menu main_menu){
        getMenuInflater().inflate(R.menu.main_menu, main_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                main_drawerlayout.openDrawer(GravityCompat.START);
                break;
            case R.id.follow:
                follow_flag = true;
                invalidateOptionsMenu();
                Toast.makeText(this, "follow", Toast.LENGTH_SHORT).show();
                break;
            case R.id.unfollow:
                follow_flag = false;
                invalidateOptionsMenu();
                Toast.makeText(this, "unfollow", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "you clicked Setting", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if (follow_flag) {
            menu.findItem(R.id.follow).setVisible(false);
        }
        else {
            menu.findItem(R.id.unfollow).setVisible(false);
        }
        return super .onPrepareOptionsMenu(menu);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        Button dialog_btn = (Button) findViewById(R.id.address_btn);
        dialog_btn.setText(dialog_items[arg0]);
    }
}