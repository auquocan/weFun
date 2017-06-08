package com.spqa.wefun.fragment;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.spqa.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.spqa.wefun.include.AppController;
import com.spqa.wefun.include.Const;
import com.spqa.wefun.include.NewFeedAdapter;
import com.spqa.wefun.R;
import com.spqa.wefun.listener.OnLoadMoreListener;
import com.spqa.wefun.object.NewFeeds;
//import com.spqa.materialviewpager.R;
//import com.github.florent37.materialviewpager.sample.R;
//import com.github.florent37.materialviewpager.sample.TestRecyclerViewAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class Fragment_Home extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 3;
    private SwipeRefreshLayout swipeContainer;
    public static final List<NewFeeds> postArr_list = new ArrayList<>();

    private String TAG = "JR";
    // These tags will be used to cancel the requests
    private String tag_json_string = "jarray_req";
    //  TextView textTest;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static Fragment_Home newInstance() {
        return new Fragment_Home();
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

    /*making JSON Request and add to arr*/

    private void makeJsonReq() {
        Toast.makeText(getActivity(), "vo ok ", Toast.LENGTH_SHORT).show();
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Const.URL_STRING_REQ, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                String jsonStr = response.toString();

                if(jsonStr != null)
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String msgErr = jsonObj.getString("error");
                    // Getting JSON Array node
                    JSONArray posts = jsonObj.getJSONArray("tasks");

                    // looping through All Post
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject c = posts.getJSONObject(i);

                        int id = c.getInt("id");
                        int type = c.getInt("type");
                        String link = c.getString("link");
                        String user = c.getString("user");
                        String cap = c.getString("cap");
                        int likes = c.getInt("likes");
                        int cmt = c.getInt("cmt");
                        int status = c.getInt("status");
                        String createdAt = c.getString("createdAt");
                        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                        Date creaAt = null;
                        try {
                            creaAt = df.parse(createdAt);
                            //String newDateString = df.format(creaAt);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        // Phone node is JSON Object
//                        JSONObject phone = c.getJSONObject("phone");
//                        String mobile = phone.getString("mobile");
//                        String home = phone.getString("home");
//                        String office = phone.getString("office");


//                        // tmp hash map for single postMap
//                        HashMap<String, String>postMap = new HashMap<>();
//
//                        // adding each child node to HashMap key => value
//                        postMap.put("id",  id);
//                        postMap.put("type", type);
//                        postMap.put("link", link);
//                        postMap.put("user", user);
//                        postMap.put("cap", cap);
//                        postMap.put("likes", likes);
//                        postMap.put("cmt", cmt);
//                        postMap.put("status", status);
//                        postMap.put("createdAt", createdAt);
                        // adding postMap to postMap list
//                        contactList.add(postMap);

                        NewFeeds feed = new NewFeeds();
                        feed.setID(id);
                        feed.setTypePost(type);
                        feed.setUserID(user);
                        feed.setCaption(cap);
                        feed.setLink(link);
                        feed.setLikes(likes);
                        feed.setComments(cmt);
                        feed.setStatus(status);
                        feed.setCreated_at(creaAt);
                       // Toast.makeText(getActivity(), id +"  - " + type  + " - " + user + " - " + cap + " - " + link + " - " + likes  +" - " + cmt +" - " + status + " - " + createdAt , Toast.LENGTH_LONG).show();
                        postArr_list.add(feed);

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
                    final NewFeedAdapter feedAdapter = new NewFeedAdapter(mRecyclerView);
                    mRecyclerView.setAdapter(feedAdapter);

                    //Toast.makeText(getActivity(),"Mess err: " +  msgErr , Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Getting JSON Array node
              //  JSONArray contacts = jsonObj.getJSONArray("contacts");


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "Error: " + error.getMessage());

            }
        });
//
//        for (int i = 0; i < ITEM_COUNT; ++i) {
//
//        }

        AppController.getInstance().addToRequestQueue(strReq, tag_json_string);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //get Json from server and add them to arr
        makeJsonReq();

        Toast.makeText(getActivity(), "sau khi get json", Toast.LENGTH_SHORT).show();



//
//        //load more
//        feedAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                Log.e("haint", "Load More ok ok ");
//                postArr_list.add(null);
//                feedAdapter.notifyItemInserted(postArr_list.size() - 1);
//
//                //Load more data for reyclerview
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e("haint", "Load More 2");
//
//                        //Remove loading item
//                        postArr_list.remove(postArr_list.size() - 1);
//                        feedAdapter.notifyItemRemoved(postArr_list.size());
//
//                        //Load data
//                        int index = postArr_list.size();
//                        int end = index + 3;
//                        for (int i = index; i < end; i++) {
//                            NewFeeds feed = new NewFeeds();
//                            feed.setID(i);
//                            feed.setTypePost(1);
//                            feed.setUserID("USer" + i);
//                            feed.setCaption("Content" + i );
//                            feed.setLink("Link"+ i);
//                            feed.setLikes(i);
//                            feed.setComments(i);
//                            feed.setStatus(1);
//                            feed.setCreated_at(new Date());
//                            postArr_list.add(feed);
//                        }
//                        feedAdapter.notifyDataSetChanged();
//                        feedAdapter.setLoaded();
//                    }
//                }, 5000);
//            }
//        });
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

                        Toast.makeText(getActivity(), "Load Xong ", Toast.LENGTH_SHORT).show();

                        swipeContainer.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
}
