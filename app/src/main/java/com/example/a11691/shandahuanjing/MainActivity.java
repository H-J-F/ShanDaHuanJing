package com.example.a11691.shandahuanjing;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements OnPageChangeListener{
    DecimalFormat dcmfmt = new DecimalFormat("0.00");
    DecimalFormat dcm_so2 = new DecimalFormat("0.0");
    Random ran = new Random();
    String tem;
    String jiaquan;
    String pm25;
    String shidu;
    String co;
    String so2;
    String no2;
    private SwipeRefreshLayout swipeRefresh;
    private DrawerLayout main_drawerlayout;
    private View view1, view2, view3, view4, view5, view6, view7, view8, view9, view_container;
    private List<View> viewList;
    private MyViewPager viewPager;
    private ArrayList<String> dialog_items = new ArrayList<String>(){{add("CD座"); add("EF座"); add("至诚"); add("G座"); add("二三饭"); add("四饭"); add("弘毅"); add("知行"); add("思源");}};
    private String tables[] = {"dorm_CD", "dorm_EF", "dorm_ZHICHENG", "dorm_G", "CANTEEN23", "CANTEEN4", "dorm_HONGYI", "dorm_ZHIXING", "dorm_SIYUAN"};
    public boolean follow_flag = false;
    private MyDatabaseHelper dbHelper;
    private String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);
        main_drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        View headerlayout = navView.inflateHeaderView(R.layout.nav_header);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        TextView date_tv = (TextView) findViewById(R.id.date);
        TextView updateTime_tv = (TextView) findViewById(R.id.updateTime);

        dbHelper = new MyDatabaseHelper(this, "AllData.db", null, 1);
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query("dorm_CD", null, null, null, null, null, null);
        Cursor personalCursor = database.query("MyInfo", null, null, null, null, null, null);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_map);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(MainActivity.this, Map.class);
                MainActivity.this.startActivity(mapIntent);
            }
        });

        Button dialog_btn = (Button) findViewById(R.id.address_btn);
        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        viewPager = (MyViewPager) findViewById(R.id.viewpager);
        LayoutInflater inflater=getLayoutInflater();
        view1 = inflater.inflate(R.layout.viewpager_layout, null);
        view2 = inflater.inflate(R.layout.viewpager_layout, null);
        view3 = inflater.inflate(R.layout.viewpager_layout, null);
        view4 = inflater.inflate(R.layout.viewpager_layout, null);
        view5 = inflater.inflate(R.layout.viewpager_layout, null);
        view6 = inflater.inflate(R.layout.viewpager_layout, null);
        view7 = inflater.inflate(R.layout.viewpager_layout, null);
        view8 = inflater.inflate(R.layout.viewpager_layout, null);
        view9 = inflater.inflate(R.layout.viewpager_layout, null);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);
        viewList.add(view6);
        viewList.add(view7);
        viewList.add(view8);
        viewList.add(view9);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);

        if (cursor.getCount() == 0){
            refreshData(0);
        }else {
            String date = new String();
            String updateTime = new String();
            for (int i = 0; i < viewList.size(); i++){
                Cursor cusr  = database.query(tables[i], null, null, null, null, null, null);
                cusr.moveToLast();

                tem = cusr.getString(cusr.getColumnIndex("temperature"));
                jiaquan = cusr.getString(cusr.getColumnIndex("jiaquan"));
                pm25 = cusr.getString(cusr.getColumnIndex("pm25"));
                shidu = cusr.getString(cusr.getColumnIndex("shidu"));
                co = cusr.getString(cusr.getColumnIndex("Co"));
                so2 = cusr.getString(cusr.getColumnIndex("So2"));
                no2 = cusr.getString(cusr.getColumnIndex("No2"));
                date = cusr.getString(cusr.getColumnIndex("date"));
                updateTime = cusr.getString(cusr.getColumnIndex("updateTime"));

                view_container = viewList.get(i);
                TextView tem_tv = (TextView) view_container.findViewById(R.id.tem);
                TextView jiaquan_tv = (TextView) view_container.findViewById(R.id.jiaquan_data);
                TextView pm25_tv = (TextView) view_container.findViewById(R.id.pm25_data);
                TextView shidu_tv = (TextView) view_container.findViewById(R.id.shidu_data);
                TextView co_tv = (TextView) view_container.findViewById(R.id.co_data);
                TextView so2_tv = (TextView) view_container.findViewById(R.id.so2_data);
                TextView no2_tv = (TextView) view_container.findViewById(R.id.no2_data);

                tem_tv.setText(tem);
                jiaquan_tv.setText(jiaquan);
                pm25_tv.setText(pm25);
                shidu_tv.setText(shidu);
                co_tv.setText(co);
                so2_tv.setText(so2);
                no2_tv.setText(no2);
            }
            date_tv.setText(date);
            updateTime_tv.setText(updateTime);
        }

        if (personalCursor.getCount() == 0){
            viewPager.setCurrentItem(0, false);
            ContentValues values = new ContentValues();
            values.put("id", 1);
            values.put("followPlace", "");
            values.put("username", "");
            values.put("myImg", "");
            values.put("declaration", "");
            values.put("mainBgImg", "");
            values.put("headBgImg", "");

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert("MyInfo", null, values);
            values.clear();
        }else {
            personalCursor.moveToLast();
            place = personalCursor.getString(personalCursor.getColumnIndex("followPlace"));
            if (!place.equals("")){
                viewPager.setCurrentItem(dialog_items.indexOf(place), false);
            }
            else {
                viewPager.setCurrentItem(0, false);
            }
        }

        for(int i = 0; i < 9; i++){
            viewList.get(i).findViewById(R.id.card_jiaquan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view_container = viewList.get(viewPager.getCurrentItem());
                    TextView jiaquan_tv = (TextView) view_container.findViewById(R.id.jiaquan_data);

                    Intent jiaquan_intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "甲醛");
                    bundle.putString("value", jiaquan_tv.getText().toString());
                    jiaquan_intent.putExtras(bundle);
                    jiaquan_intent.setClass(MainActivity.this, DataShow.class);
                    startActivity(jiaquan_intent);
                }
            });

            viewList.get(i).findViewById(R.id.card_pm25).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view_container = viewList.get(viewPager.getCurrentItem());
                    TextView pm25_tv = (TextView) view_container.findViewById(R.id.pm25_data);

                    Intent pm25_intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "PM2.5");
                    bundle.putString("value", pm25_tv.getText().toString());
                    pm25_intent.putExtras(bundle);
                    pm25_intent.setClass(MainActivity.this, DataShow.class);
                    startActivity(pm25_intent);
                }
            });

            viewList.get(i).findViewById(R.id.card_shidu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view_container = viewList.get(viewPager.getCurrentItem());
                    TextView shidu_tv = (TextView) view_container.findViewById(R.id.shidu_data);

                    Intent shidu_intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "湿度");
                    bundle.putString("value", shidu_tv.getText().toString());
                    shidu_intent.putExtras(bundle);
                    shidu_intent.setClass(MainActivity.this, DataShow.class);
                    startActivity(shidu_intent);
                }
            });

            viewList.get(i).findViewById(R.id.card_co).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view_container = viewList.get(viewPager.getCurrentItem());
                    TextView co_tv = (TextView) view_container.findViewById(R.id.co_data);

                    Intent co_intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "CO");
                    bundle.putString("value", co_tv.getText().toString());
                    co_intent.putExtras(bundle);
                    co_intent.setClass(MainActivity.this, DataShow.class);
                    startActivity(co_intent);
                }
            });

            viewList.get(i).findViewById(R.id.card_so2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view_container = viewList.get(viewPager.getCurrentItem());
                    TextView so2_tv = (TextView) view_container.findViewById(R.id.so2_data);

                    Intent so2_intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "CO");
                    bundle.putString("value", so2_tv.getText().toString());
                    so2_intent.putExtras(bundle);
                    so2_intent.setClass(MainActivity.this, DataShow.class);
                    startActivity(so2_intent);
                }
            });

            viewList.get(i).findViewById(R.id.card_no2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view_container = viewList.get(viewPager.getCurrentItem());
                    TextView no2_tv = (TextView) view_container.findViewById(R.id.no2_data);

                    Intent no2_intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "CO");
                    bundle.putString("value", no2_tv.getText().toString());
                    no2_intent.putExtras(bundle);
                    no2_intent.setClass(MainActivity.this, DataShow.class);
                    startActivity(no2_intent);
                }
            });
        }

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_message:
                        Intent per_intent = new Intent(MainActivity.this, PersonalActivity.class);
                        startActivity(per_intent);
                }
                return true;
            }
        });

        swipeRefresh.setColorSchemeResources(R.color.blue, R.color.green, R.color.purple);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewPager.setScrollable(false);
                refreshData(1300);
            }
        });
    }

    private void refreshData(final int sleepTime) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ContentValues values = new ContentValues();

                        TextView date_tv = (TextView) findViewById(R.id.date);
                        TextView updateTime_tv = (TextView) findViewById(R.id.updateTime);

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formatDate = format.format(new Date());
                        StringTokenizer stringTokenizer = new StringTokenizer(formatDate, " ");
                        String date = stringTokenizer.nextToken();
                        String updateTime = stringTokenizer.nextToken();

                        date_tv.setText(date);
                        updateTime_tv.setText(updateTime);

                        for (int i = 0; i < viewList.size(); i++){
                            view_container = viewList.get(i);
                            TextView tem_tv = (TextView) view_container.findViewById(R.id.tem);
                            TextView jiaquan_tv = (TextView) view_container.findViewById(R.id.jiaquan_data);
                            TextView pm25_tv = (TextView) view_container.findViewById(R.id.pm25_data);
                            TextView shidu_tv = (TextView) view_container.findViewById(R.id.shidu_data);
                            TextView co_tv = (TextView) view_container.findViewById(R.id.co_data);
                            TextView so2_tv = (TextView) view_container.findViewById(R.id.so2_data);
                            TextView no2_tv = (TextView) view_container.findViewById(R.id.no2_data);

                            int ran_tem = ran.nextInt(37)%(37 - 10 + 1) + 10;
                            float ran_jiaquan = ran.nextFloat();
                            int ran_pm25 = ran.nextInt(71)%(71 - 10 + 1) + 10;
                            int ran_shidu = ran.nextInt(101)%(101 - 30 + 1) + 10;
                            int ran_co = ran.nextInt(31);
                            float ran_so2 = ran.nextFloat() * 30;
                            float ran_no2 = ran.nextFloat();

                            tem = ran_tem + "";
                            jiaquan = dcmfmt.format(ran_jiaquan) + "mg/m³";
                            pm25 = ran_pm25 + "ug/m³";
                            shidu = ran_shidu + "%RH";
                            co = ran_co + "mg/m³";
                            so2 = dcm_so2.format(ran_so2) + "mg/m³";
                            no2 = dcmfmt.format(ran_no2) + "mg/m³";

                            values.put("id", 1);
                            values.put("temperature", tem);
                            values.put("jiaquan", jiaquan);
                            values.put("pm25", pm25);
                            values.put("shidu", shidu);
                            values.put("Co", co);
                            values.put("So2", so2);
                            values.put("No2", no2);
                            values.put("date", date);
                            values.put("updateTime", updateTime);

                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            db.delete(tables[i], "id > ?", new String[] {"0"});
                            db.insert(tables[i], null, values);
                            values.clear();

                            tem_tv.setText(tem);
                            jiaquan_tv.setText(jiaquan);
                            pm25_tv.setText(pm25);
                            shidu_tv.setText(shidu);
                            co_tv.setText(co);
                            so2_tv.setText(so2);
                            no2_tv.setText(no2);
                        }
                        swipeRefresh.setRefreshing(false);
                        viewPager.setScrollable(true);
                    }
                });
            }
        }).start();
    }

    private void showDialog(){
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialog_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layout, null);
        dialog.setContentView(dialog_view);

        TextView dia_CD = (TextView) dialog_view.findViewById(R.id.dialog_CD);
        TextView dia_EF = (TextView) dialog_view.findViewById(R.id.dialog_EF);
        TextView dia_ZC = (TextView) dialog_view.findViewById(R.id.dialog_zhicheng);
        TextView dia_G = (TextView) dialog_view.findViewById(R.id.dialog_G);
        TextView dia_23Fan = (TextView) dialog_view.findViewById(R.id.dialog_23fan);
        TextView dia_4Fan = (TextView) dialog_view.findViewById(R.id.dialog_4fan);
        TextView dia_HY = (TextView) dialog_view.findViewById(R.id.dialog_hongyi);
        TextView dia_ZX = (TextView) dialog_view.findViewById(R.id.dialog_zhixing);
        TextView dia_SY = (TextView) dialog_view.findViewById(R.id.dialog_siyuan);

        List<TextView> TV_List = new ArrayList<>();
        TV_List.add(dia_CD);
        TV_List.add(dia_EF);
        TV_List.add(dia_ZC);
        TV_List.add(dia_G);
        TV_List.add(dia_23Fan);
        TV_List.add(dia_4Fan);
        TV_List.add(dia_HY);
        TV_List.add(dia_ZX);
        TV_List.add(dia_SY);

        for (int i = 0; i < TV_List.size(); i++) {
            final int index = i;
            TV_List.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager = (MyViewPager) findViewById(R.id.viewpager);
                    Button dialog_btn = (Button) findViewById(R.id.address_btn);
                    dialog_btn.setText(dialog_items.get(index));
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
                if (follow_flag) {
                    follow_flag = false;
                    place = "";
                    invalidateOptionsMenu();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("followPlace", "");
                    db.update("MyInfo", values, "id = ?", new String[] {"1"});
                    Toast.makeText(this, "unfollow", Toast.LENGTH_SHORT).show();
                }
                else {
                    Button btn = (Button) findViewById(R.id.address_btn);
                    place = btn.getText().toString();
                    follow_flag = true;
                    invalidateOptionsMenu();
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("followPlace", place);
                    db.update("MyInfo", values, "id = ?", new String[] {"1"});
                    Toast.makeText(this, "follow", Toast.LENGTH_SHORT).show();
                }
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
            menu.findItem(R.id.follow).setIcon(R.drawable.ic_follow);
        }
        else {
            menu.findItem(R.id.follow).setIcon(R.drawable.ic_unfollow);
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
        viewPager.setTag(viewPager.getCurrentItem());
        Button dialog_btn = (Button) findViewById(R.id.address_btn);
        String tempPlace = dialog_items.get(arg0);
        dialog_btn.setText(tempPlace);
        follow_flag = tempPlace.equals(place);
        invalidateOptionsMenu();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        main_drawerlayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}