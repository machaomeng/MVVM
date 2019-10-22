package com.lxb.mvvmproject.ui.activity.mediarecorder;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import com.lxb.mvvmproject.base.BaseViewModel;
import com.lxb.mvvmproject.bean.MusicBean;
import com.lxb.mvvmproject.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MediaRecorderModel extends BaseViewModel {


    public MediaRecorderModel(@NonNull Application application) {
        super(application);
    }

    public void startRecord() {

    }

    public void stopRecord() {

    }

    /**
     * 获取本地音乐数据
     *
     * @param activity 上下文
     * @return
     */
    public List<MusicBean> getMusic(Activity activity) {
        ContentResolver contentResolver = activity.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        List<MusicBean> musicList = new ArrayList<>();
        if (cursor != null)
            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    MusicBean m = new MusicBean();
                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    int ismusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));

                    if (ismusic != 0 && duration / (500 * 60) >= 1) {
                        m.setId(id);
                        m.setTitle(title);
                        m.setArtist(artist);
                        m.setDuration(duration);
                        m.setSize(size);
                        m.setUrl(url);
                        m.setAlbum(album);
                        m.setAlbum_id(album_id);
                        musicList.add(m);
                    }
                    cursor.moveToNext();
                }
            }
        return musicList;
    }

    public static class TimeHandler extends Handler {
        // SoftReference<Activity> 也可以使用软应用 只有在内存不足的时候才会被回收
        private final WeakReference<MediaRecorderActivity> mActivity;

        public TimeHandler(MediaRecorderActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MediaRecorderActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == 1) {

                }
            }
        }
    }
}
