package com.example.herve.localmedia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.herve.localmedia.R;
import com.example.herve.localmedia.lisntener.OnAdapterItemClickListener;
import com.example.herve.localmedia.utils.pickmedia.PickMediaTotal;

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
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    public static final String MEDIA_TYPE_PHOTO = "photo";
    public static final String MEDIA_TYPE_VIDEO = "video";
    public static final String MEDIA_TYPE_AUDIO = "audio";
    private ArrayList<PickMediaTotal> data;
    private OnAdapterItemClickListener onAdapterItemClickListener;
    private String MEDIA_TYPE;
    private Context mContext;

    public FolderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<PickMediaTotal> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public String getMEDIA_TYPE() {
        return MEDIA_TYPE;
    }

    public void setMEDIA_TYPE(String MEDIA_TYPE) {
        this.MEDIA_TYPE = MEDIA_TYPE;
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_folder_layout, parent, false);
        return new FolderViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(final FolderViewHolder holder, final int position) {
        PickMediaTotal pickMediaTotal = data.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onAdapterItemClickListener != null) {
                    onAdapterItemClickListener.OnAdapterItemClickListener(holder, holder.itemView, position);
                }

            }
        });

        holder.tvFolderName.setText(pickMediaTotal.getFolderName());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_folder_name)
        TextView tvFolderName;

        public FolderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener onAdapterItemClickListener) {
        this.onAdapterItemClickListener = onAdapterItemClickListener;
    }
}
