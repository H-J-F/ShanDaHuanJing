package com.example.a11691.shandahuanjing;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Map extends AppCompatActivity {

    public static List<MarkerInfoUtil> infos = new ArrayList<MarkerInfoUtil>();
    private Boolean showMarker = false;
    private MapView mapview;
    private BaiduMap baiduMap;
    private LinearLayout rl_marker;
    private BitmapDescriptor bitmapDescriptor;
    private MyDatabaseHelper dbHelper;
    private String place;
    private ArrayList<String> place_items = new ArrayList<String>(){{add("CD座"); add("EF座"); add("至诚"); add("G座"); add("二三饭"); add("四饭"); add("弘毅"); add("知行"); add("思源");}};
    private String placeNames[] = {"CD座宿舍", "EF座宿舍", "至诚宿舍", "G座宿舍", "第二、三饭堂", "第四饭堂", "弘毅宿舍", "知行宿舍", "思源宿舍"};
    private double placeLatitude[] = {23.4225800000, 23.4226920000, 23.4178460000, 23.4186920000, 23.4212130000, 23.4179770000, 23.4170840000, 23.4171000000, 23.4169100000};
    private double placeLongitude[] = {116.6388310000, 116.6384900000, 116.6448230000, 116.6444140000, 116.6397800000, 116.6445100000, 116.6395820000, 116.6351918000, 116.6355750000};
    private String tables[] = {"dorm_CD", "dorm_EF", "dorm_ZHICHENG", "dorm_G", "CANTEEN23", "CANTEEN4", "dorm_HONGYI", "dorm_ZHIXING", "dorm_SIYUAN"};
    private int place_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNoTitle();
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_layout);
        mapview = (MapView) findViewById(R.id.map_view);
        baiduMap = mapview.getMap();
        rl_marker = (LinearLayout) findViewById(R.id.rl_marker);
        rl_marker.setVisibility(View.GONE);
        final List map_values[] = getdata();

        dbHelper = new MyDatabaseHelper(this, "AllData.db", null, 1);
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor personalCursor = database.query("MyInfo", null, null, null, null, null, null);

        LatLng laln = new LatLng(23.419623930393097, 116.6408161244201);
        MapStatus mMapStatus = new MapStatus.Builder().target(laln).zoom(17.8f).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.setMapStatus(mMapStatusUpdate);
        setMarkerInfo();

        personalCursor.moveToLast();
        place = personalCursor.getString(personalCursor.getColumnIndex("followPlace"));
        if (place.equals("")){
            place_index = 0;
        }
        else {
            place_index = place_items.indexOf(place);
        }

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                MarkerInfoUtil infoUtil = (MarkerInfoUtil) bundle.getSerializable("info");

                //将信息显示在界面上;
                TextView tv = new TextView(Map.this);
                tv.setBackgroundResource(R.drawable.infowindow);
                tv.setPadding(20, 7, 20, 20);
                tv.setGravity(Gravity.TOP);
                tv.setTextColor(android.graphics.Color.WHITE);
                tv.setText(infoUtil.getName());
                bitmapDescriptor = BitmapDescriptorFactory.fromView(tv);
                LatLng latLng = new LatLng(infoUtil.getLatitude(), infoUtil.getLongitude());

                TextView map_tem = (TextView) findViewById(R.id.map_tem);
                TextView map_jiaquan = (TextView) findViewById(R.id.map_jiaquan);
                TextView map_pm25 = (TextView) findViewById(R.id.map_pm25);
                TextView map_shidu = (TextView) findViewById(R.id.map_shidu);
                TextView map_co = (TextView) findViewById(R.id.map_co);
                TextView map_so2 = (TextView) findViewById(R.id.map_so2);
                TextView map_no2 = (TextView) findViewById(R.id.map_no2);
                TextView map_updateTime = (TextView) findViewById(R.id.map_updateTime);

                map_tem.setText((String) map_values[infoUtil.getTableIndex()].get(0));
                map_jiaquan.setText((String) map_values[infoUtil.getTableIndex()].get(1));
                map_pm25.setText((String) map_values[infoUtil.getTableIndex()].get(2));
                map_shidu.setText((String) map_values[infoUtil.getTableIndex()].get(3));
                map_co.setText((String) map_values[infoUtil.getTableIndex()].get(4));
                map_so2.setText((String) map_values[infoUtil.getTableIndex()].get(5));
                map_no2.setText((String) map_values[infoUtil.getTableIndex()].get(6));
                map_updateTime.setText((String) map_values[infoUtil.getTableIndex()].get(7));

                InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        //隐藏infowindow
                        baiduMap.hideInfoWindow();
                    }
                };
                InfoWindow infoWindow = new InfoWindow(bitmapDescriptor, latLng, -110, listener);
                baiduMap.showInfoWindow(infoWindow);

                //将布局显示出来
                rl_marker.setVisibility(View.VISIBLE);

                //移动地标(marker)到屏幕中下方
                LatLng laln = new LatLng(infoUtil.getLatitude() + 0.0025, infoUtil.getLongitude());
                MapStatus mMapStatus = new MapStatus.Builder().target(laln).rotate(0).overlook(0).zoom(18.4f).build();
                MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                baiduMap.animateMapStatus(msu);
                return true;
            }
        });

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }
            @Override
            public void onMapClick(LatLng arg0) {
                rl_marker.setVisibility(View.GONE);
            }
        });

        this.findViewById(R.id.btn_marker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!showMarker){
                    //显示marker
                    addOverlay(infos);
                    showMarker = true;
                }else{
                    //关闭显示marker
                    baiduMap.clear();
                    baiduMap.hideInfoWindow();
                    rl_marker.setVisibility(View.GONE);
                    showMarker = false;
                }
            }
        });
    }

    private void setMarkerInfo() {
        infos = new ArrayList<MarkerInfoUtil>();
        for(int i = 0; i < placeNames.length; i++){
            infos.add(new MarkerInfoUtil(placeLatitude[i], placeLongitude[i], placeNames[i], tables[i], i));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }

    private void setNoTitle(){
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

    private void addOverlay(List<MarkerInfoUtil> infos2) {
        //清空地图
        baiduMap.clear();
        //创建marker的显示图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);
        LatLng latLng = null;
        Marker marker;
        OverlayOptions options;
        for(MarkerInfoUtil info:infos){
            //获取经纬度
            latLng = new LatLng(info.getLatitude(),info.getLongitude());
            //设置marker
            options = new MarkerOptions()
                    .position(latLng)//设置位置
                    .icon(bitmap)//设置图标样式
                    .zIndex(6) // 设置marker所在层级
                    .draggable(false); // 设置手势拖拽;
            //添加marker
            marker = (Marker) baiduMap.addOverlay(options);
            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
        //将地图显示在最后一个marker的位置
        latLng = new LatLng(infos.get(place_index).getLatitude(), infos.get(place_index).getLongitude());
        MapStatus mMapStatus = new MapStatus.Builder().rotate(0).overlook(0).target(latLng).zoom(18.4f).build();
        MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        baiduMap.animateMapStatus(msu);
    }

    private List[] getdata(){
        List map[] = new List[tables.length];
        dbHelper = new MyDatabaseHelper(this, "AllData.db", null, 1);
        final SQLiteDatabase database = dbHelper.getWritableDatabase();

        for(int i = 0; i < tables.length; i++){
            List<String> strList = new ArrayList<>();
            Cursor cursor = database.query(tables[i], null, null, null, null, null, null);
            cursor.moveToLast();

            String tem = cursor.getString(cursor.getColumnIndex("temperature"));
            String jiaquan = cursor.getString(cursor.getColumnIndex("jiaquan"));
            String pm25 = cursor.getString(cursor.getColumnIndex("pm25"));
            String shidu = cursor.getString(cursor.getColumnIndex("shidu"));
            String co = cursor.getString(cursor.getColumnIndex("Co"));
            String so2 = cursor.getString(cursor.getColumnIndex("So2"));
            String no2 = cursor.getString(cursor.getColumnIndex("No2"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String updateTime = cursor.getString(cursor.getColumnIndex("updateTime"));

            strList.add(0, tem + "℃");
            strList.add(1, jiaquan);
            strList.add(2, pm25);
            strList.add(3, shidu);
            strList.add(4, co);
            strList.add(5, so2);
            strList.add(6, no2);
            strList.add(7, date + " " + updateTime);

            map[i] = strList;
        }
        return map;
    }
}
