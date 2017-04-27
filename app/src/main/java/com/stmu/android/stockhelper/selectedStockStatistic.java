package com.stmu.android.stockhelper;

import android.app.Fragment;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by drodr on 4/21/2017.
 */
public class selectedStockStatistic extends Fragment {

    TextView mNews;
    TextView mStatistics;
    TextView mOverview;
    Bundle bundle;
    View item_add;
    View item_refresh;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.stock_select_statistics_layout, container, false);

        mOverview = (TextView)getActivity().findViewById(R.id.overview);
        mStatistics = (TextView)getActivity().findViewById(R.id.statistics);
        mNews = (TextView)getActivity().findViewById(R.id.news);

        bundle = this.getArguments();

        item_add = getActivity().findViewById(R.id.action_add);
        item_refresh = getActivity().findViewById(R.id.action_refresh);

        item_add.setVisibility(View.INVISIBLE);
        item_refresh.setVisibility(View.INVISIBLE);

        mOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOverview.setPaintFlags(mOverview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                mStatistics.setPaintFlags(0);
                mNews.setPaintFlags(0);
                selectedStockFragment nextFrag = new selectedStockFragment();
                nextFrag.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction().replace(R.id.frameLayoutFragmentContainer, nextFrag, "overviewFragment").commit();
            }
        });
        mStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatistics.setPaintFlags( mStatistics.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                mOverview.setPaintFlags(0);
                mNews.setPaintFlags(0);
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
}
