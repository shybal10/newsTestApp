package com.shybal.test.newsapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.shybal.test.newsapp.R;
import com.shybal.test.newsapp.databinding.ActivityDetailsBinding;
import com.shybal.test.newsapp.model.NewsDetail;
import com.shybal.test.newsapp.viewmodel.DetailsViewModel;

/**
 * Activity class for news details page
 */
public class DetailsActivity extends BaseActivity {
    private DetailsViewModel detailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setUpBindings();
        detailsViewModel.init((NewsDetail) getIntent().getSerializableExtra("NEWS_DETAILS"));
    }


    /**
     * sets up the databinding for the activity and binds it to the view model
     */
    @Override
    protected void setUpBindings() {
        ActivityDetailsBinding activityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        activityDetailsBinding.setLifecycleOwner(this);
        activityDetailsBinding.setDetailsViewModel(detailsViewModel);
    }

}
