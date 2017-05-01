package com.spqa.wefun.include;

import android.net.Uri;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.spqa.wefun.R;
import com.spqa.wefun.fragment.Fragment_Home;
import com.spqa.wefun.listener.OnLoadMoreListener;
import com.spqa.wefun.object.NewFeeds;
import com.squareup.picasso.Picasso;
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
        return Fragment_Home.feedItems_List.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
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
            NewFeeds newFeeds = Fragment_Home.feedItems_List.get(position);
            final UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.tvContent.setText(newFeeds.getContent());

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


            DrawableRequestBuilder<String> thumbnailRequest = Glide
                    .with( userViewHolder.itemFeedView.getContext() )
                    .load( "https://media.giphy.com/media/l2JhBOWhKWH7n4c6I/giphy.gif" );

            userViewHolder.webViewGIF.loadUrl("http://gifspace.net/image/aRP4k.gif");

            //Set GIF
//            Glide.with(userViewHolder.itemFeedView.getContext())
//                    .load("https://media.giphy.com/media/l2JhBOWhKWH7n4c6I/giphy.gif")
//                    .thumbnail(0.05f)
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(new GlideDrawableImageViewTarget(userViewHolder.imgFeed))
            //
            ;
//            Glide.with(userViewHolder.itemFeedView.getContext())
//                    .load(Uri.parse("https://media1.giphy.com/media/5ziaphcUWGKPu/200.gif"))
//                    .asGif().placeholder(R.drawable.liked).crossFade()
//                    .into(userViewHolder.imgFeed);

//            // Set Image
//            String linkImage = "https://scontent.fsgn5-2.fna.fbcdn.net/v/t31.0-8/s960x960/14361354_1329888960362351_7640614371886677007_o.jpg?oh=f9947b52a138e293f5e2533c8117ed81&oe=597C8CF5";
//            Picasso.with(userViewHolder.itemFeedView.getContext()).load(linkImage).into(userViewHolder.imgFeed);
           // userViewHolder.tvEmailId.setText(newFeeds.getEmail());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
       return Fragment_Home.feedItems_List == null ? 0 : Fragment_Home.feedItems_List.size();
       // return 10;
    }

    public void setLoaded() {
        isLoading = false;
    }
}