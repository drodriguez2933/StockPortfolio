package com.stmu.android.stockhelper.JSON;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by drodr on 3/30/2017.
 */
public class FetchData{



    public interface AsyncResponse {
        void processFinish(String output1,String output2,
                           String output3,String output4,
                           String output5,String output6,
                           String output7,String output8,
                           String output9,String output10,
                           String output11,String output12,
                           String output13, String output14,
                           String output15, String output16,
                           String output17, String output18);
    }

    public static class placeIdTask extends AsyncTask<String, Void, JSONObject> {

        public AsyncResponse delegate = null;

        public placeIdTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20%3D%20%22" + params[0] + "%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

            try {
                JSONObject json_data = JSONfunctions.getJSONfromURL(url);
                JSONObject json_query = json_data.getJSONObject("query");
                JSONObject json_results = json_query.getJSONObject("results");
                JSONObject json_quote = json_results.getJSONObject("quote");

                return json_quote;

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(JSONObject json) {
            if(json != null) {
                try {
                    String symbol = json.getString("symbol");
                    String price = json.getString("LastTradePriceOnly");
                    String pct = json.getString("Change");
                    String name = json.getString("Name");
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String formattedDate = df.format(c.getTime());
                    String change = json.getString("ChangeinPercent");
                    String close = json.getString("PreviousClose");
                    String open = json.getString("Open");
                    String low = json.getString("DaysLow");
                    String high = json.getString("DaysHigh");
                    String fiftyTwoWeekLow = json.getString("YearLow");
                    String fiftyTwoWeekHigh = json.getString("YearHigh");
                    String mktCap = json.getString("MarketCapitalization");
                    String volume = json.getString("Volume");
                    String oneYearTarget = json.getString("OneyrTargetPrice");
                    String avgVol = json.getString("AverageDailyVolume");
                    String eps = json.getString("EPSEstimateCurrentYear");
                    String pe = json.getString("PERatio");
                    delegate.processFinish(symbol,price,
                            pct,name,formattedDate,
                            change,close,
                            open,low,
                            high,fiftyTwoWeekLow,
                            fiftyTwoWeekHigh,mktCap,
                            volume,oneYearTarget,
                            avgVol,eps,pe);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else{
                System.out.println("JSON is null");
            }
        }
    }
}
