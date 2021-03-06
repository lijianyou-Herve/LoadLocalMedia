package com.example.herve.localmedia.utils.pickmedia.listener;

import com.example.herve.localmedia.utils.pickmedia.PickMediaTotal;
import com.example.herve.localmedia.utils.pickmedia.PickMessage;

import java.util.ArrayList;

/**
 * Created by Herve on 2016/7/20.
 */
public interface PickMediaCallBack {
    public static final String PICK_MEDIA_PHOTO = "photo";
    public static final String PICK_MEDIA_VIDEO = "video";
    public static final String PICK_MEDIA_AUDIO = "audio";

    void onStart();

    void onSuccess(ArrayList<PickMediaTotal> list, String PICK_MEDIA);

    void onError(PickMessage pickMessage);
}
