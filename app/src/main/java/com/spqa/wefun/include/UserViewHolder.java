package com.spqa.wefun.include;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.spqa.wefun.R;

import pl.droidsonroids.gif.GifImageButton;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by MyPC on 24/04/2017.
 */

class UserViewHolder extends RecyclerView.ViewHolder {
    public TextView tvContent;
    public ImageView imgFeed;
    public View itemFeedView;
    public ImageView imgLike;
    public WebView webViewGIF;
    public GifImageView gifView;

    public UserViewHolder(View itemView) {
        super(itemView);
        tvContent = (TextView) itemView.findViewById(R.id.feed_caption);
        itemFeedView = itemView;
        webViewGIF = (WebView) itemView.findViewById(R.id.webViewGIF);
        imgFeed = (ImageView) itemView.findViewById(R.id.imgFeed);
        imgLike = (ImageView) itemView.findViewById(R.id.imgLike);
        gifView = (GifImageView) itemView.findViewById(R.id.gifView);
    }
}
