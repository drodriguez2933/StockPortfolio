package com.stmu.android.stockhelper;

import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stmu.android.stockhelper.Database.StockCRUD;

import java.text.DecimalFormat;

/**
 * Created by drodr on 4/18/2017.
 */
public class selectedStockFragment extends Fragment {

    TextView stockTickerSymbol;
    TextView stockName;
    TextView stockPrice;
    TextView stockPCT;

    TextView mCloseTextView;
    TextView mOpenTextView;
    TextView mLowTextView;
    TextView mHighTextView;
    TextView mFiftyTwoHighTextView;
    TextView mFiftyTwoLowTextView;
    TextView mMktCap;
    TextView mVolume;
    TextView mOneYrTarget;
    TextView mAvgVol;
    TextView mEPS;
    TextView mPE;

    TextView mOverview;
    TextView mStatistics;
    TextView mNews;
    Bundle bundle;
    View item_refresh;
    View item_add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.stock_select_layout, container, false);

        LinearLayout ll = (LinearLayout)getActivity().findViewById(R.id.frameLayoutFragmentContainer1);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
        params.height = 0;
        ll.setLayoutParams(params);

        LinearLayout ll2 = (LinearLayout)getActivity().findViewById(R.id.frameLayoutFragmentContainer2);
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) ll2.getLayoutParams();
        params2.height = 100;
        ll2.setLayoutParams(params2);

        item_add = getActivity().findViewById(R.id.action_add);
        item_refresh = getActivity().findViewById(R.id.action_refresh);

        item_add.setVisibility(View.INVISIBLE);
        item_refresh.setVisibility(View.INVISIBLE);

        mOverview = (TextView)getActivity().findViewById(R.id.overview);
        mStatistics = (TextView)getActivity().findViewById(R.id.statistics);
        mNews = (TextView)getActivity().findViewById(R.id.news);

        mOverview.setPaintFlags(mOverview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mStatistics.setPaintFlags(0);
        mNews.setPaintFlags(0);

        bundle = this.getArguments();
        final String stockTicker= bundle.getString("stockTickerSymbol");

        updateView(stockTicker, view);

        //System.out.println(stockTicker);

        mOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOverview.setPaintFlags( mOverview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                mStatistics.setPaintFlags(0);
                mNews.setPaintFlags(0);
            }
        });
        mStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatistics.setPaintFlags( mStatistics.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                mOverview.setPaintFlags(0);
                mNews.setPaintFlags(0);
                selectedStockStatistic nextFrag = new selectedStockStatistic();
                nextFrag.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction().replace(R.id.frameLayoutFragmentContainer,nextFrag,"statisticFragment").commit();
            }
        });
        mNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNews.setPaintFlags( mNews.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                mStatistics.setPaintFlags(0);
                mOverview.setPaintFlags(0);
                selectedStockNews nextFrag = new selectedStockNews();
                nextFrag.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction().replace(R.id.frameLayoutFragmentContainer,nextFrag,"newsFragment").commit();
            }
        });

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        item_add.setVisibility(View.VISIBLE);
        item_refresh.setVisibility(View.VISIBLE);
    }

    public void updateView(String tickerSymbol, View view){
        stockTickerSymbol = (TextView)view.findViewById(R.id.stock_ticker_symbol);
        stockTickerSymbol.setText(tickerSymbol);

        StockCRUD stockDb = new StockCRUD(getActivity());
        stockDb.open();
        Cursor cursor = stockDb.getStock(tickerSymbol);

        stockPrice = (TextView)view.findViewById(R.id.stock_price);
        stockPrice.setText(cursor.getString(2));

        stockPCT = (TextView)view.findViewById(R.id.stock_percentage);
        stockPCT.setText(cursor.getString(3)+cursor.getString(6));

        Character c = cursor.getString(3).charAt(0);
        if(c == '-'){
            stockPCT.setBackgroundColor(Color.argb(100, 120,0, 0));
        }else if(c == '+'){
            stockPCT.setBackgroundColor(Color.argb(100,0,120,0));
        }

        stockName = (TextView)view.findViewById(R.id.stock_name);
        stockName.setText(cursor.getString(4));

        mOpenTextView = (TextView)view.findViewById(R.id.stock_open);
        mOpenTextView.setText(cursor.getString(7));

        mCloseTextView = (TextView)view.findViewById(R.id.stock_prev_close);
        mCloseTextView.setText(cursor.getString(8));

        mLowTextView = (TextView)view.findViewById(R.id.stock_low);
        mLowTextView.setText(cursor.getString(9));

        mHighTextView = (TextView)view.findViewById(R.id.stock_high);
        mHighTextView.setText(cursor.getString(10));

        mFiftyTwoLowTextView = (TextView)view.findViewById(R.id.stock_fifty_two_week_low);
        mFiftyTwoLowTextView.setText(cursor.getString(11));

        mFiftyTwoHighTextView = (TextView)view.findViewById(R.id.stock_fifty_two_week_high);
        mFiftyTwoHighTextView.setText(cursor.getString(12));

        mMktCap = (TextView)view.findViewById(R.id.stock_mkt_cap);
        mMktCap.setText(cursor.getString(13));

        mVolume = (TextView)view.findViewById(R.id.stock_volume);
        int temp_volume = Integer.parseInt(cursor.getString(14));
        double mutliplyer = 0.000001;
        DecimalFormat df = new DecimalFormat("0.##");
        String result = df.format(Double.valueOf(temp_volume * mutliplyer));
        mVolume.setText(String.valueOf(result + "M"));

        mOneYrTarget = (TextView)view.findViewById(R.id.stock_one_year_target);
        mOneYrTarget.setText(cursor.getString(15));

        mAvgVol = (TextView)view.findViewById(R.id.stock_avg_volue);
        temp_volume = Integer.parseInt(cursor.getString(16));
        mutliplyer = 0.000001;
        df = new DecimalFormat("0.##");
        result = df.format(Double.valueOf(temp_volume * mutliplyer));
        mAvgVol.setText(String.valueOf(result + "M"));

        mEPS = (TextView)view.findViewById(R.id.stock_earnings_per_share);
        mEPS.setText(cursor.getString(17));

        mPE = (TextView)view.findViewById(R.id.stock_price_earnings);
        mPE.setText(cursor.getString(18));


    }
}
