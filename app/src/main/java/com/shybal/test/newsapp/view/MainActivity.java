package com.shybal.test.newsapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.shybal.test.newsapp.R;
import com.shybal.test.newsapp.databinding.ActivityMainBinding;
import com.shybal.test.newsapp.model.NewsDetail;
import com.shybal.test.newsapp.model.SearchData;
import com.shybal.test.newsapp.viewmodel.MainViewModel;

import java.util.LinkedList;
import java.util.Objects;

/**
 * launcher activity for the application
 */
public class MainActivity extends BaseActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_main);
        setUpBindings();
        mainViewModel.initialiseComponents();
        setUpDataObservers();
    }

    /**
     * sets up the databinding for the activity and binds it to the view model
     */
    @Override
    protected void setUpBindings() {
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        activityMainBinding.setLifecycleOwner(this);
        activityMainBinding.setMainViewModel(mainViewModel);
    }

    /**
     * sets up the observers of the bound view model
     */
    protected void setUpDataObservers() {
        mainViewModel.getDisplayMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
        mainViewModel.fetchList().observe(this, new Observer<LinkedList<NewsDetail>>() {
            @Override
            public void onChanged(@Nullable LinkedList<NewsDetail> news) {
                mainViewModel.populateInAdapter(news);
            }
        });
        mainViewModel.getSelected().observe(MainActivity.this, new Observer<NewsDetail>() {
            @Override
            public void onChanged(@Nullable NewsDetail newsDetail) {
                openNewsDetailsPage(newsDetail);
            }
        });
        mainViewModel.getSearchButtonClick().observe(this, new Observer<SearchData>() {
            @Override
            public void onChanged(SearchData searchData) {
                hideKeyBoard();
            }
        });
        mainViewModel.getDataUpdateAvailable().observe(MainActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean updateAvailable) {
                if (updateAvailable) showUpdateDialog();
            }
        });
    }

    /**
     * shows the alert dialog when 24 hours have passed since the last API call and an update
     * is available
     */
    private void  showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Update Data ?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        mainViewModel.onSearchClick();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * hides the keyboard on search button click
     */
    private void hideKeyBoard() {
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * opens the DetailsActivity when the user selects a news item
     * @param newsDetail an {@link NewsDetail} object that contain the data to be show in the details page
     */
    private void openNewsDetailsPage(NewsDetail newsDetail) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("NEWS_DETAILS", newsDetail);
        startActivity(intent);
    }
}
