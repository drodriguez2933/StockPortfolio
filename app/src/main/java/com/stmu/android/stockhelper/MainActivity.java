package com.stmu.android.stockhelper;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.stmu.android.stockhelper.Database.StockCRUD;
import com.stmu.android.stockhelper.Database.StockDataBase;
import com.stmu.android.stockhelper.JSON.FetchData;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Fragment mainFragment;
    addStockDialog dialogFragment;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(mToolBar);

        setTitle("My Portfolio");

        fm = getFragmentManager();
        mainFragment = fm.findFragmentById(R.id.frameLayoutFragmentContainer);

        if (mainFragment == null) {
            mainFragment = new FragmentOneLayout();
            fm.beginTransaction().add(R.id.frameLayoutFragmentContainer, mainFragment,"Fragment").commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Vibrator vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        switch (item.getItemId()) {
            case R.id.action_refresh:
                //User chooses action refresh button

                StockCRUD temp = new StockCRUD(getApplicationContext());
                temp.open();
                temp.getAllSymbols();
                getFragmentManager().beginTransaction().detach(mainFragment).commit();
                getFragmentManager().beginTransaction().attach(mainFragment).commit();
                vib.vibrate(100);
                //Toast.makeText(getApplicationContext(),"REFRESH",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_add:
                //User chooses button to add stock
                vib.vibrate(100);
                showAddDialog();
                dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        getFragmentManager().beginTransaction().detach(mainFragment).commit();
                        getFragmentManager().beginTransaction().attach(mainFragment).commit();
                    }
                });
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void showAddDialog() {
        FragmentManager fm = getFragmentManager();
        dialogFragment = addStockDialog.newInstance("Add Stock");
        dialogFragment.show(fm, "fragment_add_stock");
    }

    @Override
    public void onBackPressed() {
        final Fragment fragment = getFragmentManager().findFragmentById(R.id.frameLayoutFragmentContainer);
        if (fragment != null) {
            getFragmentManager().beginTransaction().replace(R.id.frameLayoutFragmentContainer, mainFragment,null).commit();
        } else {
            super.onBackPressed();
        }
    }
    }
