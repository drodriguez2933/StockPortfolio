package com.stmu.android.stockhelper;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.stmu.android.stockhelper.Database.StockCRUD;
import com.stmu.android.stockhelper.Database.StockDataBase;

import org.w3c.dom.Text;

/**
 * Created by drodr on 3/22/2017.
 */
public class FragmentOneLayout  extends Fragment {

    private StockCRUD stockDb;
    public static final int REQUEST_CODE = 1;
    Boolean checkYesNo;
    ListView myList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout, container, false);



        stockDb = new StockCRUD(getActivity());
        stockDb.open();

        populateListView(view);
        setListListener();

        LinearLayout ll2 = (LinearLayout)getActivity().findViewById(R.id.frameLayoutFragmentContainer2);
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) ll2.getLayoutParams();
        params2.height = 0;
        ll2.setLayoutParams(params2);

        LinearLayout ll = (LinearLayout)getActivity().findViewById(R.id.frameLayoutFragmentContainer1);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
        params.height = 100;
        ll.setLayoutParams(params);

        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void populateListView(View view) {
        Cursor cursor = stockDb.getAllStock();
        int cnt = 0;
        String[] fromFieldNames = new String[]{StockDataBase.COLUMN_TITLE, StockDataBase.COLUMN_PRICE,
                StockDataBase.COLUMN_PCT, StockDataBase.COLUMN_NAME,
                StockDataBase.COLUMN_DATE, StockDataBase.COLUMN_CHANGE};
        int[] toViewIds = new int[]{R.id.stock_symbol, R.id.stock_price, R.id.stock_change, R.id.stock_name, R.id.stock_date, R.id.stock_percentage};

        SimpleCursorAdapter myCursorAdaptor;
        myCursorAdaptor = new SimpleCursorAdapter(getActivity(), R.layout.fragment_list_item, cursor, fromFieldNames, toViewIds, 0);

        myList = (ListView) view.findViewById(R.id.list_view);
        myList.setAdapter(myCursorAdaptor);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    // here the part where I get my selected date from the saved variable in the intent and the displaying it.
                    Bundle bundle = data.getExtras();
                    checkYesNo = bundle.getBoolean("checkYesNO");
                }
                break;
        }
    }

    public void setListListener(){
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStockFragment nextFrag = new selectedStockFragment();
                Bundle bundle = new Bundle();
                TextView tv = (TextView)view.findViewById(R.id.stock_symbol);
                String stockSymbol = tv.getText().toString();
                bundle.putString("stockTickerSymbol", stockSymbol);
                nextFrag.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction().replace(R.id.frameLayoutFragmentContainer,nextFrag,"overviewFragment").commit();
            }
        });


        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View temp_view, final int position, long id) {
                deleteStockDialog deleteStock = new deleteStockDialog();
                deleteStock.setTargetFragment(FragmentOneLayout.this, REQUEST_CODE);
                deleteStock.show(getActivity().getFragmentManager(), "delete_dialog");

                deleteStock.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (checkYesNo == true) {
                            TextView tv = (TextView)temp_view.findViewById(R.id.stock_symbol);
                            String stockSymbol = tv.getText().toString();
                            //System.out.println(stockSymbol);
                            StockCRUD stockDB = new StockCRUD(getActivity());
                            stockDB.open();
                            stockDB.deleteStock(stockSymbol);
                            stockDB.close();
                            populateListView(getView());
                        } else if (checkYesNo == false) {
                            //System.out.println("False");
                        }

                    }
                });

                return true;
            }
        });
    }
}