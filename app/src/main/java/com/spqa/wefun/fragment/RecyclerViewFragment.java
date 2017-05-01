package com.spqa.wefun.fragment;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.spqa.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.spqa.wefun.MainActivity;
import com.spqa.wefun.include.NewFeedAdapter;
import com.spqa.wefun.include.TestRecyclerViewAdapter;
import com.spqa.wefun.R;
import com.spqa.materialviewpager.MaterialViewPagerHelper;
import com.spqa.wefun.listener.OnLoadMoreListener;
//import com.spqa.materialviewpager.R;
//import com.github.florent37.materialviewpager.sample.R;
//import com.github.florent37.materialviewpager.sample.TestRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {
    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;
    private SwipeRefreshLayout swipeContainer;
    //  TextView textTest;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // swipeContainer = (SwipeRefreshLayout) getView().findViewById(R.id.swiper);

        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        /////////////////////
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swiper);

        swipeContainer.setMinimumHeight(1000);

        // Configure the refreshing colo
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
////////////////////////////
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final List<Object> items = new ArrayList<>();

        for (int i = 0; i < ITEM_COUNT; ++i) {
            items.add(new Object());
        }


        //setup materialviewpager

//        if (GRID_LAYOUT) {
//            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        } else {
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        }
   //set layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        NewFeedAdapter feedAdapter = new NewFeedAdapter(mRecyclerView);
        mRecyclerView.setAdapter(feedAdapter);

        //load more
        feedAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });
        //Refresh Data
        reFresher();
    }

    private void reFresher() {
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 更新が終了したらインジケータ非表示
                        Toast.makeText(getActivity(), "Load Xong ", Toast.LENGTH_SHORT).show();
                        swipeContainer.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
}
