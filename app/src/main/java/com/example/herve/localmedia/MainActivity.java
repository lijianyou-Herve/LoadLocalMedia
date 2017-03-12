package com.example.herve.localmedia;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.herve.localmedia.adapter.FolderAdapter;
import com.example.herve.localmedia.adapter.MediaItemAdapter;
import com.example.herve.localmedia.lisntener.OnAdapterItemClickListener;
import com.example.herve.localmedia.utils.pickmedia.MediaBean;
import com.example.herve.localmedia.utils.pickmedia.PickMediaHelper;
import com.example.herve.localmedia.utils.pickmedia.PickMediaTotal;
import com.example.herve.localmedia.utils.pickmedia.PickMessage;
import com.example.herve.localmedia.utils.pickmedia.listener.PickMediaCallBack;
import com.example.herve.localmedia.utils.toast.ToastAlone;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    @BindView(R.id.rv_folder)
    RecyclerView rvFolder;
    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.btn_read_audio)
    Button btnReadAudio;
    @BindView(R.id.btn_read_video)
    Button btnReadVideo;
    @BindView(R.id.btn_read_Photo)
    Button btnReadPhoto;
    @BindView(R.id.ll_read_media)
    LinearLayout llReadMedia;

    private FolderAdapter folderAdapter;
    private MediaItemAdapter mediaItemAdapter;

    private PickMediaHelper pickMediaHelper;

    private ArrayList<MediaBean> mediaBeanArrayList = new ArrayList<MediaBean>();

    private Context mContext;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        initView();
        initData();

        initListener();

    }

    private void initView() {
        folderAdapter = new FolderAdapter(mContext);
        mediaItemAdapter = new MediaItemAdapter(mContext);

        RecyclerView.LayoutManager folderLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        rvFolder.setLayoutManager(folderLayoutManager);
        rvFolder.setAdapter(folderAdapter);

        RecyclerView.LayoutManager mediaLayoutManager = new GridLayoutManager(mContext, 4, RecyclerView.VERTICAL, false);
        rvItems.setLayoutManager(mediaLayoutManager);
        rvItems.setAdapter(mediaItemAdapter);

        progressBar.setVisibility(View.GONE);

    }

    private void initListener() {
        btnReadAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickMediaHelper.startReadAudio();

            }
        });
        btnReadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickMediaHelper.startReadVideo();

            }
        });
        btnReadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickMediaHelper.startReadPhoto();

            }
        });

        folderAdapter.setOnAdapterItemClickListener(new OnAdapterItemClickListener() {
            @Override
            public void OnAdapterItemClickListener(RecyclerView.ViewHolder holder, View view, int position) {
                mediaBeanArrayList.clear();

                switch (folderAdapter.getMEDIA_TYPE()) {
                    case FolderAdapter.MEDIA_TYPE_PHOTO:
                        mediaBeanArrayList.addAll(pickMediaHelper.getPhotoChildPathList(position));
                        break;
                    case FolderAdapter.MEDIA_TYPE_VIDEO:
                        mediaBeanArrayList.addAll(pickMediaHelper.getVideoChildPathList(position));

                        break;
                    case FolderAdapter.MEDIA_TYPE_AUDIO:
                        mediaBeanArrayList.addAll(pickMediaHelper.getAudioChildPathList(position));

                        break;
                    default:
                        break;
                }

                mediaItemAdapter.setData(mediaBeanArrayList);
            }
        });

        mediaItemAdapter.setOnAdapterItemClickListener(new OnAdapterItemClickListener() {
            @Override
            public void OnAdapterItemClickListener(RecyclerView.ViewHolder holder, View view, int position) {

                ToastAlone.showToast(mContext, mediaBeanArrayList.get(position).getOriginalFilePath(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void initData() {
        pickMediaHelper = new PickMediaHelper(mContext);
        refRefreshPhoto();
    }

    private void refRefreshPhoto() {
        pickMediaHelper.readVideoListener(new PickMediaCallBack() {
            @Override
            public void onStart() {
                Log.e("readVideoListener", " onStart() 开始获取媒体库");
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<PickMediaTotal> list, String PICK_MEDIA) {
                Log.e("readVideoListener", " onSuccess() 开始获取视频媒体库");
                folderAdapter.setMEDIA_TYPE(PICK_MEDIA);
                folderAdapter.setData(list);
                mediaBeanArrayList.clear();
                if (list.size() > 0) {
                    mediaBeanArrayList.addAll(pickMediaHelper.getVideoChildPathList(0));
                    mediaItemAdapter.setData(mediaBeanArrayList);
                }
                progressBar.setVisibility(View.GONE);
                //读取成功，返回 list，直接丢入到 ListView 适配器中
            }

            @Override
            public void onError(PickMessage pickMessage) {
                Log.e("readVideoListener", " onError() 获取媒体库失败");
                progressBar.setVisibility(View.GONE);
            }
        });

        pickMediaHelper.readPhotoListener(new PickMediaCallBack() {
            @Override
            public void onStart() {
                Log.e("readPhotoListener", " onStart() 开始获取媒体库");
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<PickMediaTotal> list, String PICK_MEDIA) {
                folderAdapter.setMEDIA_TYPE(PICK_MEDIA);
                folderAdapter.setData(list);
                mediaBeanArrayList.clear();
                if (list.size() > 0) {
                    mediaBeanArrayList.addAll(pickMediaHelper.getPhotoChildPathList(0));
                    mediaItemAdapter.setData(mediaBeanArrayList);
                }
                progressBar.setVisibility(View.GONE);

                Log.e("readPhotoListener", " onSuccess() 开始获取图片媒体库");
            }

            @Override
            public void onError(PickMessage pickMessage) {
                progressBar.setVisibility(View.GONE);
                Log.e("readPhotoListener", " onError() 获取媒体库失败");
            }
        });

        pickMediaHelper.readAudioListener(new PickMediaCallBack() {
            @Override
            public void onStart() {
                Log.e("readAudioListener", " onStart() 开始获取媒体库");
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(ArrayList<PickMediaTotal> list, String PICK_MEDIA) {
                folderAdapter.setMEDIA_TYPE(PICK_MEDIA);
                folderAdapter.setData(list);
                mediaBeanArrayList.clear();
                if (list.size() > 0) {
                    mediaBeanArrayList.addAll(pickMediaHelper.getAudioChildPathList(0));
                    mediaItemAdapter.setData(mediaBeanArrayList);
                }
                progressBar.setVisibility(View.GONE);

                Log.e("readAudioListener", " onSuccess() 开始获取图片媒体库");
            }

            @Override
            public void onError(PickMessage pickMessage) {
                progressBar.setVisibility(View.GONE);
                Log.e("readAudioListener", " onError() 获取媒体库失败");
            }
        });


    }


}
