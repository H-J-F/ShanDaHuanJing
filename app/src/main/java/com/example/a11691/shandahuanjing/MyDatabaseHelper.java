package com.example.a11691.shandahuanjing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 11691 on 2017/5/2.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_CD = "create table if not exists dorm_CD ("
            + "id integer primary key autoincrement, "
            + "temperature text, "
            + "jiaquan text, "
            + "pm25 text, "
            + "shidu text, "
            + "Co text, "
            + "So2 text, "
            + "No2 text, "
            + "date text, "
            + "updateTime text)";

    public static final String CREATE_EF = "create table if not exists dorm_EF ("
            + "id integer primary key autoincrement, "
            + "temperature text, "
            + "jiaquan text, "
            + "pm25 text, "
            + "shidu text, "
            + "Co text, "
            + "So2 text, "
            + "No2 text, "
            + "date text, "
            + "updateTime text)";

    public static final String CREATE_ZHICHENG = "create table if not exists dorm_ZHICHENG ("
            + "id integer primary key autoincrement, "
            + "temperature text, "
            + "jiaquan text, "
            + "pm25 text, "
            + "shidu text, "
            + "Co text, "
            + "So2 text, "
            + "No2 text, "
            + "date text, "
            + "updateTime text)";

    public static final String CREATE_G = "create table if not exists dorm_G ("
            + "id integer primary key autoincrement, "
            + "temperature text, "
            + "jiaquan text, "
            + "pm25 text, "
            + "shidu text, "
            + "Co text, "
            + "So2 text, "
            + "No2 text, "
            + "date text, "
            + "updateTime text)";

    public static final String CREATE_CANTEEN23 = "create table if not exists CANTEEN23 ("
            + "id integer primary key autoincrement, "
            + "temperature text, "
            + "jiaquan text, "
            + "pm25 text, "
            + "shidu text, "
            + "Co text, "
            + "So2 text, "
            + "No2 text, "
            + "date text, "
            + "updateTime text)";

    public static final String CREATE_CANTEEN4 = "create table if not exists CANTEEN4 ("
            + "id integer primary key autoincrement, "
            + "temperature text, "
            + "jiaquan text, "
            + "pm25 text, "
            + "shidu text, "
            + "Co text, "
            + "So2 text, "
            + "No2 text, "
            + "date text, "
            + "updateTime text)";

    public static final String CREATE_HONGYI = "create table if not exists dorm_HONGYI ("
            + "id integer primary key autoincrement, "
            + "temperature text, "
            + "jiaquan text, "
            + "pm25 text, "
            + "shidu text, "
            + "Co text, "
            + "So2 text, "
            + "No2 text, "
            + "date text, "
            + "updateTime text)";

    public static final String CREATE_ZHIXING = "create table if not exists dorm_ZHIXING ("
            + "id integer primary key autoincrement, "
            + "temperature text, "
            + "jiaquan text, "
            + "pm25 text, "
            + "shidu text, "
            + "Co text, "
            + "So2 text, "
            + "No2 text, "
            + "date text, "
            + "updateTime text)";

    public static final String CREATE_SIYUAN = "create table if not exists dorm_SIYUAN ("
            + "id integer primary key autoincrement, "
            + "temperature text, "
            + "jiaquan text, "
            + "pm25 text, "
            + "shidu text, "
            + "Co text, "
            + "So2 text, "
            + "No2 text, "
            + "date text, "
            + "updateTime text)";

    public static final String CREATE_MYINFO = "create table if not exists MyInfo ("
            + "id integer primary key autoincrement, "
            + "followPlace text, "
            + "username text, "
            + "myImg text, "
            + "declaration text, "
            + "mainBgImg text, "
            + "headBgImg text)";

    public static final String CREATE_REFERENCE = "create table if not exists ReferenceData ("
            + "id integer primary key autoincrement, "
            + "temperature text, "
            + "jiaquan text, "
            + "pm25 text, "
            + "shidu text, "
            + "Co text, "
            + "So2 text, "
            + "No2 text, "
            + "updateTime text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CD);
        db.execSQL(CREATE_EF);
        db.execSQL(CREATE_ZHICHENG);
        db.execSQL(CREATE_G);
        db.execSQL(CREATE_CANTEEN23);
        db.execSQL(CREATE_CANTEEN4);
        db.execSQL(CREATE_HONGYI);
        db.execSQL(CREATE_ZHIXING);
        db.execSQL(CREATE_SIYUAN);
        db.execSQL(CREATE_MYINFO);
        db.execSQL(CREATE_REFERENCE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
