package com.example.herve.localmedia.utils.pickmedia.pick_photo;

/**
 * Created           :Herve on 2016/8/29.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/8/29
 * @ projectName     :LocalMedia
 * @ version
 */
public abstract class PickPhotoThread extends Thread implements Runnable {
    public abstract void pickPhotoThreadRun();

    @Override
    public void run() {
        pickPhotoThreadRun();
    }
}
