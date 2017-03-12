package com.example.herve.localmedia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.herve.localmedia.R;
import com.example.herve.localmedia.lisntener.OnAdapterItemClickListener;
import com.example.herve.localmedia.utils.pickmedia.MediaBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created           :Herve on 2017/3/9.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2017/3/9
 * @ projectName     :LocalMedia
 * @ version
 */
public class MediaItemAdapter extends RecyclerView.Adapter<MediaItemAdapter.MediaItemViewHolder> {

    private ArrayList<MediaBean> data;
    private OnAdapterItemClickListener onAdapterItemClickListener;
    private Context mContext;

    public MediaItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<MediaBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MediaItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_media_item_layout, parent, false);
        return new MediaItemViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(final MediaItemViewHolder holder, final int position) {
        String filePath = data.get(position).getOriginalFilePath();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onAdapterItemClickListener != null) {
                    onAdapterItemClickListener.OnAdapterItemClickListener(holder, holder.itemView, position);
                }

            }
        });

        if (filePath.endsWith("mp4") || filePath.endsWith("jpg") || filePath.endsWith("png")) {
            Glide.with(mContext).load(data.get(position).getOriginalFilePath()).into(holder.ivMediaCover);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class MediaItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_media_cover)
        ImageView ivMediaCover;

        public MediaItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener onAdapterItemClickListener) {
        this.onAdapterItemClickListener = onAdapterItemClickListener;
    }

}
