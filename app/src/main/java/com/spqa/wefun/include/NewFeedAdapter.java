package com.spqa.wefun.include;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;
import android.util.DisplayMetrics;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.spqa.wefun.R;
import com.spqa.wefun.fragment.Fragment_Home;
import com.spqa.wefun.listener.OnLoadMoreListener;
import com.spqa.wefun.object.NewFeeds;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import pl.droidsonroids.gif.GifDrawable;
//import com.transitionseverywhere.extra.Scale;

/**
 * Created by MyPC on 24/04/2017.
 */

public class NewFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    // public List<User> mUsers ;
    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public NewFeedAdapter(RecyclerView rcv) {
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rcv.getLayoutManager();
        rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return Fragment_Home.postArr_list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
//        switch (position) {
//            case 0:
//                return VIEW_TYPE_ITEM;
//            default:
//                return VIEW_TYPE_LOADING;
//        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_image, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            final NewFeeds newFeeds = Fragment_Home.postArr_list.get(position);
            final UserViewHolder userViewHolder = (UserViewHolder) holder;

            //set caption cho bài post
            userViewHolder.tvContent.setText(newFeeds.getCaption());

            //change icon like
            userViewHolder.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(userViewHolder.imgLike.getTag().toString()) == 1) {
                        //Animation
                        ScaleAnimation animation = new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(300);
                        userViewHolder.imgLike.setAnimation(animation);
                        //userViewHolder.imgLike.setBackgroundResource(R.drawable.liked);
                        userViewHolder.imgLike.setImageResource(R.drawable.liked);
                        userViewHolder.imgLike.setTag(2);
//                        ////Animation
//                        final ViewGroup transitionsContainer = (ViewGroup) userViewHolder.itemFeedView;
//                        TransitionSet set = new TransitionSet()
//
//                                .addTransition(new Fade());
////.addTransition(new Scale(0.5f))
//                        TransitionManager.beginDelayedTransition(transitionsContainer, set);


                    } else {
                        ScaleAnimation animation = new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        animation.setDuration(300);
                        userViewHolder.imgLike.setAnimation(animation);
                        // userViewHolder.imgLike.setBackgroundResource(R.drawable.like);
                        userViewHolder.imgLike.setImageResource(R.drawable.like);
                        userViewHolder.imgLike.setTag(1);
                    }
                    //  userViewHolder.imgLike.setImageResource(R.drawable.liked);


                }
            });


//test load GIF by WEbview
//            Picasso.with(userViewHolder.itemFeedView.getContext()).load("http://gifspace.net/image/aRP4k.gif").into(userViewHolder.imgFeed);
////            userViewHolder.imgFeed.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
//////                    userViewHolder.webViewGIF.setVisibility(View.VISIBLE);
//////                    userViewHolder.webViewGIF.loadUrl("http://gifspace.net/image/aRP4k.gif");
////                    userViewHolder.imgFeed.setVisibility(View.INVISIBLE);
////                }
////            });

            //webview
//            userViewHolder.webViewGIF.loadUrl("http://gifspace.net/image/aRP4k.gif");
//        userViewHolder.webViewGIF.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                userViewHolder.webViewGIF.setVisibility(View.INVISIBLE);
//                userViewHolder.imgFeed.setVisibility(View.VISIBLE);
//                return false;
//            }
//        });

//            String linkImage = "http://gifspace.net/image/aRP4k.gif";
//            //Set GIF
//           Glide.with(userViewHolder.itemFeedView.getContext())
//                    .load(linkImage)
//                   .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                   .into(new GlideDrawableImageViewTarget(userViewHolder.imgFeed))
            //
            ;
//            Glide.with(userViewHolder.itemFeedView.getContext())
//                    .load(Uri.parse("https://media1.giphy.com/media/5ziaphcUWGKPu/200.gif"))
//                    .asGif().placeholder(R.drawable.liked).crossFade()
//                    .into(userViewHolder.imgFeed);


            final String linkImage = "https://media.giphy.com/media/l0Iy9g9pvspPyJIju/giphy.gif";
            switch (newFeeds.getTypePost()) {
                case 1:
                    userViewHolder.gifView.setVisibility(View.VISIBLE);
                    userViewHolder.imgFeed.setVisibility(View.INVISIBLE);
//                    Picasso.with(userViewHolder.itemFeedView.getContext()).load(newFeeds.getLink()).into(userViewHolder.imgFeed);
                    //InputStream (it must support marking)

                    try {
                        String sourceIs = "http://s2.cdn.xiachufang.com/9748367af81b11e6bc9d0242ac110002_350w_262h.gif?imageView2/2/w/200/interlace/1/q/90/format/gif/.gif";
                        InputStream stream = new ByteArrayInputStream(sourceIs.getBytes(StandardCharsets.UTF_8));
                        BufferedInputStream bis = new BufferedInputStream( stream , 100 );
                        GifDrawable gifFromStream = new GifDrawable(bis);
                        userViewHolder.gifView.setImageDrawable(gifFromStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


//                    Glide.with(userViewHolder.itemFeedView.getContext())
//                            .load("http://s2.cdn.xiachufang.com/9748367af81b11e6bc9d0242ac110002_350w_262h.gif?imageView2/2/w/200/interlace/1/q/90/format/gif/.gif")
//                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                            .crossFade(500)
//                            .into(userViewHolder.gifView);
//                    Glide.with(userViewHolder.itemFeedView.getContext())
//                            .load("http://gifspace.net/image/aRP4k.gif")
//
//                            .into(new GlideDrawableImageViewTarget(userViewHolder.gifView));
//                    Glide.with(userViewHolder.itemFeedView.getContext())
//                            .load("https://media.giphy.com/media/l0Iy9g9pvspPyJIju/giphy.gif")
//                            .asGif().crossFade()
//                            .into(userViewHolder.imgFeed);

                    break;
                case 2:

                    userViewHolder.imgFeed.setVisibility(View.GONE);
                    userViewHolder.gifView.setVisibility(View.VISIBLE);
                    loadGif(userViewHolder, newFeeds.getLink());
                    break;

            }


            // userViewHolder.tvEmailId.setText(newFeeds.getEmail());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    private void loadGif(final UserViewHolder userViewHolder, final String link) {
        // Set Image
//
//    //Picasso.with(userViewHolder.itemFeedView.getContext()).load(link).into(userViewHolder.imgFeed);
//    Picasso.with(userViewHolder.itemFeedView.getContext()).load(link).into(new Target() {
//        @Override
//        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            userViewHolder.imgFeed.setImageBitmap(bitmap);
//
//        }
//
//        @Override
//        public void onBitmapFailed(Drawable errorDrawable) {
//            //xử lý lỗi nếu ko load thành công image
//        }
//
//        @Override
//        public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//        }
//    });
//
//    userViewHolder.imgFeed.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            userViewHolder.imgFeed.setVisibility(View.INVISIBLE);
//            //webview
//            userViewHolder.webViewGIF.setVisibility(View.VISIBLE);
//
//
//
//
//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            ((Activity)userViewHolder.itemFeedView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
//            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
//
//
//            Toast.makeText(userViewHolder.itemFeedView.getContext(), dpHeight + "test width: " + dpWidth, Toast.LENGTH_SHORT).show();
//
////            String url = "https://media.giphy.com/media/l0Iy9g9pvspPyJIju/giphy.gif";
//
//
//            String data="<img src="+link+" style='width:"+dpWidth+"px; margin-left:-6pt' />";
//            userViewHolder.webViewGIF.loadData(data, "text/html", "utf-8");
//
//
//
////                    userViewHolder.webViewGIF.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            Toast.makeText(userViewHolder.itemFeedView.getContext(), "OKOK click", Toast.LENGTH_SHORT).show();
////                        }
////                    });
//
//        }
//    });
        userViewHolder.gifView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Glide
//                        .with(userViewHolder.itemFeedView.getContext())
//                        .load("https://media2.giphy.com/avatars/nikdudukovic/ylDRTR05sy6M.gif")
//                        .asBitmap()
//                        .into(new SimpleTarget<Bitmap>(100, 100) {
//                            @Override
//                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//                                userViewHolder.gifView.setImageBitmap(resource); // Possibly runOnUiThread()
//                            }
//                        });

                Glide.with(userViewHolder.itemFeedView.getContext())
                    .load("https://media.giphy.com/media/l0Iy9g9pvspPyJIju/giphy.gif")
                    .into(userViewHolder.gifView);
//                Glide
//                        .with(userViewHolder.itemFeedView.getContext())
//                        .load("https://media2.giphy.com/avatars/nikdudukovic/ylDRTR05sy6M.gif")
//                        .asBitmap()
//                        .into(userViewHolder.gifView
//                        );
                Toast.makeText(userViewHolder.itemFeedView.getContext(), "test click Gif " + userViewHolder.tvContent.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return Fragment_Home.postArr_list == null ? 0 : Fragment_Home.postArr_list.size();
        // return 10;
    }

    public void setLoaded() {
        isLoading = false;
    }
}