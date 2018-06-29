package com.mengyi.app.dribbbo.view.shot_list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mengyi.app.dribbbo.view.base.BaseViewHolder;
import com.mengyi.app.dribbbo.R;

import butterknife.BindView;

class ShotViewHolder extends BaseViewHolder {

    @BindView(R.id.shot_clickable_cover) View cover;
    @BindView(R.id.shot_like_count) TextView likeCount;
    @BindView(R.id.shot_bucket_count) TextView bucketCount;
    @BindView(R.id.shot_view_count) TextView viewCount;
    @BindView(R.id.shot_image) ImageView image;

    public ShotViewHolder(View itemView) {
        super(itemView);
    }
}
