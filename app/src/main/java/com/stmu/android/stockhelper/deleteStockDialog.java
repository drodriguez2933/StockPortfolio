package com.stmu.android.stockhelper;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stmu.android.stockhelper.Database.StockCRUD;
import com.stmu.android.stockhelper.JSON.FetchData;

import org.w3c.dom.Text;

/**
 * Created by drodr on 4/10/2017.
 */
public class deleteStockDialog extends DialogFragment {

    StockCRUD stockDb;
    DialogInterface.OnDismissListener onDismissListener;

    Button mYesButton;
    Button mNoButton;
    TextView mTextView;
    Boolean checkYesNO;

    public deleteStockDialog(){
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
        return inflater.inflate(R.layout.delete_stock_dialog_fragment, container);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkYesNO = false;

        mTextView = (TextView)getActivity().findViewById(R.id.delete_stoc_text_view);

        mYesButton = (Button)view.findViewById(R.id.yes_button);
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(100);
                checkYesNO = true;
                dismiss();
            }
        });

        mNoButton = (Button)view.findViewById(R.id.no_button);
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(100);
                checkYesNO = false;
                dismiss();
            }
        });

    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Intent intent = new Intent();
        intent.putExtra("checkYesNO",checkYesNO);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
}
