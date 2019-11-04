package com.shybal.test.newsapp.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.shybal.test.newsapp.BR;
import com.shybal.test.newsapp.model.NewsDetail;
import com.shybal.test.newsapp.viewmodel.MainViewModel;
import java.util.List;

/**
 * class that acts as a view adapter for the news list Recyclerview
 */
public class NewsResultsRecViewAdapter extends RecyclerView.Adapter<NewsResultsRecViewAdapter.ItemViewHolder> {
    private MainViewModel mainViewModel;
    private int layoutRes;
    private List<NewsDetail> news;

    public NewsResultsRecViewAdapter(MainViewModel mainViewModel, @LayoutRes int LayoutRes) {
        this.mainViewModel = mainViewModel;
        this.layoutRes = LayoutRes;
    }

    /**
     * returns the bound layout file
     * @param position the position of the item to be view recycled
     * @return the layout id
     */
    @Override
    public int getItemViewType(int position) {
        return layoutRes;
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return the total number of news items
     */
    @Override
    public int getItemCount() {
        return news == null ? 0 : news.size();
    }

    /**
     *  sets the total number of news items to be set by the adapter
     * @param news list of newsItems to be held by the adapter
     */
    public void setNews(List<NewsDetail> news) {
        this.news = news;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, i, viewGroup, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.bind(mainViewModel,i);
    }


    /**
     * view holder class for the Recycler view
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;
        ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(MainViewModel mainViewModel,Integer position) {
            binding.setVariable(BR.mainViewModel,mainViewModel);
            binding.setVariable(BR.position,position);
            binding.executePendingBindings();
        }
    }
}
