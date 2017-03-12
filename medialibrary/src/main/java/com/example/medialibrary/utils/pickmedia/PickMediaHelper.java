package com.example.mylibrary.utils.pickmedia;

import android.content.Context;

import com.example.herve.localmedia.utils.pickmedia.pick_audio.PickAudio;
import com.example.herve.localmedia.utils.pickmedia.pick_audio.model.SongBean;
import com.example.herve.localmedia.utils.pickmedia.pick_photo.PickPhoto;
import com.example.herve.localmedia.utils.pickmedia.pick_video.PickVideo;

import java.util.List;

import static com.example.herve.localmedia.utils.pickmedia.PickMediaHelper.PIckMediaType.Audio;
import static com.example.herve.localmedia.utils.pickmedia.PickMediaHelper.PIckMediaType.Photo;
import static com.example.herve.localmedia.utils.pickmedia.PickMediaHelper.PIckMediaType.Video;

/**
 * Created by hupei on 2016/7/14.
 */
public class PickMediaHelper<T extends MediaBean> {
    private PickMeaid mPickMeaid;

    private PickMediaHelper(Context context, PickMediaCallBack callback, PIckMediaType mediaType) {
        switch (mediaType) {
            case Photo:
                mPickMeaid = new PickPhoto(context.getApplicationContext(), callback);
                break;
            case Video:
                mPickMeaid = new PickVideo(context.getApplicationContext(), callback);
                break;
            case Audio:
                mPickMeaid = new PickAudio(context.getApplicationContext(), callback);
                break;
        }
        mPickMeaid.start();
    }

    /**
     * 开始读取视频
     *
     * @param context
     * @param callback
     * @return
     */
    public static PickMediaHelper<MediaBean> readVideo(Context context, PickMediaCallBack callback) {
        callback.onStart();
        return new PickMediaHelper<MediaBean>(context, callback, Video);
    }

    /**
     * 开始读取相册
     *
     * @param context
     * @param callback
     * @return
     */
    public static PickMediaHelper<MediaBean> readPhoto(Context context, PickMediaCallBack callback) {
        callback.onStart();
        return new PickMediaHelper<MediaBean>(context, callback, Photo);
    }

    /**
     * 开始读取相册
     *
     *
     * @param context
     * @param callback
     * @return
     */
    public static PickMediaHelper<SongBean> readAudio( Context context, PickMediaCallBack callback) {
        callback.onStart();

        return new PickMediaHelper<SongBean>(context, callback, Audio);
    }

    /**
     * 取出子目录图片路径集合
     *
     * @param position
     * @return
     */
    public  List<T> getChildPathList(int position) {
        if (mPickMeaid == null) return null;

        return mPickMeaid.getChildPathList(position);
    }

    enum PIckMediaType {
        Photo, Audio, Video;
    }
}
