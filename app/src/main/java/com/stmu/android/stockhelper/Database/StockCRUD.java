package com.stmu.android.stockhelper.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.stmu.android.stockhelper.JSON.FetchData;
import com.stmu.android.stockhelper.Stock;

import java.io.Externalizable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by drodr on 12/4/2016.
 */
public class StockCRUD {

    public static final String LOGTAG = "WORKOUT_CRUD";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    public static final String[] allColumns = {
            StockDataBase.COLUMN_ID,
            StockDataBase.COLUMN_TITLE,
            StockDataBase.COLUMN_PRICE,
            StockDataBase.COLUMN_PCT,
            StockDataBase.COLUMN_NAME,
            StockDataBase.COLUMN_DATE,
            StockDataBase.COLUMN_CHANGE,
            StockDataBase.COLUMN_OPEN,
            StockDataBase.COLUMN_CLOSE,
            StockDataBase.COLUMN_HIGH,
            StockDataBase.COLUMN_LOW,
            StockDataBase.COLUMN_FIFTY_TWO_WEEK_HIGH,
            StockDataBase.COLUMN_FIFTY_TWO_WEEK_LOW,
            StockDataBase.COLUMN_MKT_CAP,
            StockDataBase.COLUMN_VOLUME,
            StockDataBase.COLUMN_ONE_YEAR_TARGET,
            StockDataBase.COLUMN_AVG_VOLUME,
            StockDataBase.COLUMN_EARNINGS_PER_SHARE,
            StockDataBase.COLUMN_PRICE_EARNINGS
    };

    public StockCRUD(Context context) {
        dbhandler = new StockDataBase(context);
    }

    public void open() {
        Log.i(LOGTAG, "DATABASE OPENED");
        database = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "DATABASE CLOSED");
        dbhandler.close();
    }


    public boolean addStock(Stock stock) {
        boolean DBaccepted = true;
        ContentValues values = new ContentValues();
        values.put(StockDataBase.COLUMN_ID, getMaxId() + 1);
        values.put(StockDataBase.COLUMN_TITLE, stock.getTitle());
        values.put(StockDataBase.COLUMN_PRICE, stock.getPrice());
        values.put(StockDataBase.COLUMN_PCT, stock.getPCT());
        values.put(StockDataBase.COLUMN_NAME, stock.getName());
        values.put(StockDataBase.COLUMN_DATE, stock.getDate());
        values.put(StockDataBase.COLUMN_CHANGE, "("+stock.getChange()+")");
        values.put(StockDataBase.COLUMN_LOW,stock.getLow());
        values.put(StockDataBase.COLUMN_HIGH,stock.getHigh());
        values.put(StockDataBase.COLUMN_FIFTY_TWO_WEEK_LOW,stock.getFiftyTwoWeekLow());
        values.put(StockDataBase.COLUMN_FIFTY_TWO_WEEK_HIGH,stock.getFiftyTwoWeekHigh());
        values.put(StockDataBase.COLUMN_CLOSE,stock.getClose());
        values.put(StockDataBase.COLUMN_OPEN,stock.getOpen());
        values.put(StockDataBase.COLUMN_MKT_CAP,stock.getMktCap());
        values.put(StockDataBase.COLUMN_VOLUME,stock.getVolume());
        values.put(StockDataBase.COLUMN_ONE_YEAR_TARGET,stock.getOneYearTarget());
        values.put(StockDataBase.COLUMN_AVG_VOLUME,stock.getAvgVol());
        values.put(StockDataBase.COLUMN_EARNINGS_PER_SHARE,stock.getEps());
        values.put(StockDataBase.COLUMN_PRICE_EARNINGS,stock.getPe());
        try {
            database.insertOrThrow(StockDataBase.TABLE_STOCK, null, values);
            int temp = getMaxId();
            System.out.println(temp);
        }catch (SQLiteConstraintException e){
            System.out.println(e);
            DBaccepted = false;
        }

        return DBaccepted;
    }

    public void getAllSymbols(){
        String findCount = "SELECT " + StockDataBase.COLUMN_TITLE + " FROM " + StockDataBase.TABLE_STOCK ;

        dbhandler.getReadableDatabase();
        Cursor cursor = database.rawQuery(findCount,null);
        int cnt = 0;
        ArrayList<String> list = new ArrayList<>();

        while(cursor.moveToNext()){
            list.add(cnt,cursor.getString(0));
            cnt++;
        }

       for (int i = 0; i < list.size();i++){
           updateStock(list.get(i));
       }

        cursor.close();
    }

    public Cursor getAllStock() {
        String where = null;
        Cursor cursor = database.query(true, StockDataBase.TABLE_STOCK, allColumns, where, null, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public int getMaxId(){
        int cnt = 1;
        String findCount = "SELECT " + StockDataBase.COLUMN_ID + " FROM " + StockDataBase.TABLE_STOCK
                + " WHERE " + StockDataBase.COLUMN_ID + "=(SELECT max(" + StockDataBase.COLUMN_ID + ") FROM " + StockDataBase.TABLE_STOCK + ")";
        dbhandler.getReadableDatabase();
        Cursor cursor = database.rawQuery(findCount,null);
        cursor.moveToFirst();
            try {
                cnt = cursor.getInt(cursor.getColumnIndex(StockDataBase.COLUMN_ID));
                System.out.println(cnt);
                cnt+=1;
            }catch (Exception e){
                System.out.println("Error Caught Add Stock!!!!");
            }
        return cnt;
    }

    public void deleteStock(String symbol){
        String selection = StockDataBase.COLUMN_TITLE + " LIKE ?";
        String[] selectionArgs = { symbol };

        database.delete(StockDataBase.TABLE_STOCK, selection, selectionArgs);
    }

    public void updateStock(final String temp_string){
        FetchData.placeIdTask asyncTask = new FetchData.placeIdTask(new FetchData.AsyncResponse() {
            @Override
            public void processFinish(String symbol, String price,
                                      String pct, String name,
                                      String date,String change,
                                      String close,String open,
                                      String low, String high,
                                      String fiftytwoweeklow, String fiftytwoweekhigh,
                                      String mktCap,String volume,
                                      String oneYrTarget, String avgVol,
                                      String eps, String pe) {

                SQLiteDatabase db = dbhandler.getReadableDatabase();

                ContentValues values = new ContentValues();
                values.put(StockDataBase.COLUMN_TITLE, symbol);
                values.put(StockDataBase.COLUMN_PRICE, price);
                values.put(StockDataBase.COLUMN_PCT, pct);
                values.put(StockDataBase.COLUMN_NAME, name);
                values.put(StockDataBase.COLUMN_DATE, date);
                values.put(StockDataBase.COLUMN_CHANGE, change);
                values.put(StockDataBase.COLUMN_LOW,low);
                values.put(StockDataBase.COLUMN_HIGH,high);
                values.put(StockDataBase.COLUMN_FIFTY_TWO_WEEK_LOW,fiftytwoweeklow);
                values.put(StockDataBase.COLUMN_FIFTY_TWO_WEEK_HIGH,fiftytwoweekhigh);
                values.put(StockDataBase.COLUMN_CLOSE,close);
                values.put(StockDataBase.COLUMN_OPEN,open);
                values.put(StockDataBase.COLUMN_MKT_CAP,mktCap);
                values.put(StockDataBase.COLUMN_VOLUME,volume);
                values.put(StockDataBase.COLUMN_ONE_YEAR_TARGET,oneYrTarget);
                values.put(StockDataBase.COLUMN_AVG_VOLUME,avgVol);
                values.put(StockDataBase.COLUMN_EARNINGS_PER_SHARE,eps);
                values.put(StockDataBase.COLUMN_PRICE_EARNINGS,pe);

                // Which row to update, based on the title
                String selection = StockDataBase.COLUMN_TITLE + " LIKE ?";
                String[] selectionArgs = { temp_string };

                int count = db.update(
                        StockDataBase.TABLE_STOCK,
                        values,
                        selection,
                        selectionArgs);
            }
        });
        asyncTask.execute(temp_string);
    }

    public Cursor getStock(String tickerSymbol){
        String query = "SELECT * FROM " + StockDataBase.TABLE_STOCK + " WHERE " + StockDataBase.COLUMN_TITLE + " = '" + tickerSymbol + "';";

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();

        return cursor;
    }
}

