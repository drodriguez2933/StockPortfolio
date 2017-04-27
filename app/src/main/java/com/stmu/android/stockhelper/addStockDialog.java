package com.stmu.android.stockhelper;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stmu.android.stockhelper.Database.StockCRUD;
import com.stmu.android.stockhelper.JSON.FetchData;

/**
 * Created by drodr on 3/23/2017.
 */
public class addStockDialog extends DialogFragment {

    private EditText mEditText;
    private Button mButton;
    private StockCRUD stockDb;
    private DialogInterface.OnDismissListener onDismissListener;

    public addStockDialog() {
    }

    public static addStockDialog newInstance(String title) {
        addStockDialog frag = new addStockDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        stockDb = new StockCRUD(getActivity());
        stockDb.open();
        return inflater.inflate(R.layout.add_stock_fragment_dialog, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.add_stock_edit_text);
        mEditText.setCursorVisible(false);
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
                mEditText.setCursorVisible(true);
            }
        });

        mButton = (Button) view.findViewById(R.id.add_stock_button);
        mButton.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                           vib.vibrate(100);
                                           String temp_string = mEditText.getText().toString().toUpperCase();
                                           FetchData.placeIdTask asyncTask = new FetchData.placeIdTask(new FetchData.AsyncResponse() {
                                               @Override
                                               public void processFinish(String symbol, String price,
                                                                         String pct, String name,
                                                                         String date, String change,
                                                                         String close,String open,
                                                                         String low, String high,
                                                                         String fiftytwoweeklow, String fiftytwoweekhigh,
                                                                         String mktCap, String volume,
                                                                         String oneYrTarget, String avgVol,
                                                                         String eps, String pe) {
                                                   Stock stock = new Stock();
                                                   stock.setTitle(symbol);
                                                   stock.setPrice(price);
                                                   stock.setPCT(pct);
                                                   stock.setName(name);
                                                   stock.setDate(date);
                                                   stock.setChange(change);
                                                   stock.setLow(low);
                                                   stock.setHigh(high);
                                                   stock.setClose(close);
                                                   stock.setOpen(open);
                                                   stock.setFiftyTwoWeekHigh(fiftytwoweekhigh);
                                                   stock.setFiftyTwoWeekLow(fiftytwoweeklow);
                                                   stock.setMktCap(mktCap);
                                                   stock.setVolume(volume);
                                                   stock.setOneYearTarget(oneYrTarget);
                                                   stock.setAvgVol(avgVol);
                                                   stock.setEps(eps);
                                                   stock.setPe(pe);

                                                   //System.out.println("High: " + high + " Low: " + low + " 52High: " + fiftytwoweekhigh + " 52Low: " + fiftytwoweeklow + " Close: " + close + " Open: " + open);
                                                   if (price.equals("null")) {
                                                       Toast.makeText(getActivity(), "Stock doesn't exist!", Toast.LENGTH_LONG).show();
                                                       dismiss();
                                                   } else {
                                                       boolean DBaccepted = stockDb.addStock(stock);
                                                       if (DBaccepted == false) {
                                                           Toast.makeText(getActivity(), "Stock already tracked!", Toast.LENGTH_SHORT).show();
                                                       } else {
                                                           Toast.makeText(getActivity(), "Stock added!", Toast.LENGTH_SHORT).show();
                                                       }
                                                       dismiss();
                                                   }
                                               }
                                           });
                                           asyncTask.execute(temp_string);
                                       }
                                   }

        );
    }


    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
            stockDb.close();
        }
    }

}
