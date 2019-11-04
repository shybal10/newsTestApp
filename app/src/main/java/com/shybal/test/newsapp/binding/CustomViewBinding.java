package com.shybal.test.newsapp.binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.shybal.test.newsapp.utils.OnSearchCloseSoftKeyboardListener;
import com.squareup.picasso.Picasso;

/**
 * class holding all the binding adapter methods used to bind the view
 */
public class CustomViewBinding {

    /**
     * method to to bind the Recyclerview to a view adapter
     * @param recyclerView a {@link RecyclerView} object
     * @param adapter an adapter for the recyclerview
     */
    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    /**
     * method to load the image url to the ImageView using Picasso Library
     * @param view a {@link ImageView} object
     * @param imageUrl the url to be loaded into the Imageview
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        if (!(imageUrl!= null && imageUrl.isEmpty())) {
            Picasso.get()
                    .load(imageUrl)
                    .resize(2048, 1600)
                    .onlyScaleDown()
                    .into(view);
        }
    }

    /**
     * the method generates a callback for a search action from the keyboard
     * @param view a {@link TextView} object
     * @param listener {@link OnSearchCloseSoftKeyboardListener} object implemented by viewModel
     */
    @BindingAdapter("onSearchCloseSoftKeyboard")
    public static void setOnOkInSoftKeyboardListener(TextView view, final OnSearchCloseSoftKeyboardListener listener) {
        if (listener == null) {
            view.setOnEditorActionListener(null);
        } else {
            view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        listener.onSearchCloseSoftKeyboard();
                        return true;
                    }
                    return false;
                }
            });
        }
    }


}
