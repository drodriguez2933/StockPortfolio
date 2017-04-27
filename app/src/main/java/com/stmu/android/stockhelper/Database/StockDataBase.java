package com.stmu.android.stockhelper.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;


public class StockDataBase extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "stockDatabase";

    public static final String TABLE_STOCK = "stock";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE= "title";
    public static final String COLUMN_PRICE= "price";
    public static final String COLUMN_PCT= "percentage";
    public static final String COLUMN_NAME= "name";
    public static final String COLUMN_DATE= "date";
    public static final String COLUMN_CHANGE= "change";
    public static final String COLUMN_OPEN="open";
    public static final String COLUMN_CLOSE="close";
    public static final String COLUMN_LOW="low";
    public static final String COLUMN_HIGH="high";
    public static final String COLUMN_FIFTY_TWO_WEEK_HIGH="fiftytwoweekhigh";
    public static final String COLUMN_FIFTY_TWO_WEEK_LOW="fiftytwoweeklow";
    public static final String COLUMN_MKT_CAP="mktcap";
    public static final String COLUMN_VOLUME="volume";
    public static final String COLUMN_ONE_YEAR_TARGET="oneyeartarget";
    public static final String COLUMN_AVG_VOLUME="avgvolume";
    public static final String COLUMN_PRICE_EARNINGS="percentearnings";
    public static final String COLUMN_EARNINGS_PER_SHARE="earningspershare";


    public StockDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKOUT_TABLE = "CREATE TABLE " + TABLE_STOCK
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TITLE + " TEXT UNIQUE," + COLUMN_PRICE + " TEXT,"
                + COLUMN_PCT + " TEXT," + COLUMN_NAME + " TEXT,"
                + COLUMN_DATE + " TEXT," + COLUMN_CHANGE + " TEXT,"
                + COLUMN_CLOSE + " TEXT," + COLUMN_OPEN + " TEXT,"
                + COLUMN_LOW + " TEXT," + COLUMN_HIGH + " TEXT,"
                + COLUMN_FIFTY_TWO_WEEK_LOW+" TEXT," + COLUMN_FIFTY_TWO_WEEK_HIGH + " TEXT,"
                + COLUMN_MKT_CAP+" TEXT," + COLUMN_VOLUME + " TEXT,"
                + COLUMN_ONE_YEAR_TARGET+" TEXT," + COLUMN_AVG_VOLUME + " TEXT,"
                + COLUMN_EARNINGS_PER_SHARE+" TEXT," + COLUMN_PRICE_EARNINGS + " TEXT)";
        db.execSQL(CREATE_WORKOUT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK);

        onCreate(db);
    }
}

