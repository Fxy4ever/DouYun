package util.MediaLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import Utils.MD5Util;

/**
 * Created by mac on 2018/4/25.
 */

public class FMediaPlayer {
    private String TAG = "FMediaPlayer";
    private MediaPlayer mediaPlayer;
    private String url;
    private SurfaceHolder surfaceHolder;
    private VideoSurfaceView surfaceView;
    private ProgressBar progressBar;
    private Context context;
    private SeekBar seekBar;
    private TextView currentTv;
    private TextView totalTv;
    private int currentPosition = 0;
    private int totalPosition = 0;
    private boolean isPrepare = false;
    private boolean isPlaying = false;
    private boolean isPause = false;
    private boolean isStop = false;
    private boolean isSetSeek = false;
    private SeekBarProgressListener listener;

    public FMediaPlayer() {
        mediaPlayer = new MediaPlayer();
    }
    public FMediaPlayer setSurfaceView(VideoSurfaceView surfaceView){
        this.surfaceView = surfaceView;
        return this;
    }
    public FMediaPlayer setContext(Context context){
        this.context = context;
        return this;
    }
    public FMediaPlayer setSeekBar(SeekBar seekBar){
        this.seekBar = seekBar;
        return this;
    }
    public FMediaPlayer setProgressBar(ProgressBar progressBar){
        this.progressBar = progressBar;
        progressBar.setVisibility(View.GONE);
        return this;
    }
    public FMediaPlayer setTextview(TextView currentTv,TextView totalTv){
        this.currentTv = currentTv;
        this.totalTv = totalTv;
        return this;
    }
    public FMediaPlayer setUrl(String url){
        this.url = url;
        return this;
    }
    public FMediaPlayer play(){
        setListener();
        initHolder();
        return this;
    }
    private void setListener(){
        MediaPlayerListener listener = new MediaPlayerListener();
        mediaPlayer.setOnPreparedListener(listener);
        mediaPlayer.setOnCompletionListener(listener);
        mediaPlayer.setOnErrorListener(listener);
        mediaPlayer.setOnVideoSizeChangedListener(sizeChangedListener);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }
    private void initHolder(){
        if(surfaceView!=null){
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    stopMediaPlayer();
                }
            });
        }
    }
    public void createMediaPlayer(){
        if(!isPrepare){
            isPlaying=true;
            isPause=false;
            Log.d(TAG, "createMediaPlayer: ");
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.setDisplay(surfaceView.getHolder());
                progressBar.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void startMediaPlayer() {
        if(isPrepare){
            if (mediaPlayer != null && isPlaying) {
                isPlaying = false;
                mediaPlayer.pause();
                Log.d(TAG, "startMediaPlayer: pause");
                return;
            }
            if (mediaPlayer != null) {
                Log.d(TAG, "startMediaPlayer: start");
                isPlaying = true;
                mediaPlayer.start();
            }
        }
    }
    public void pauseMediaPlayer() {
        if(!isPause){
            isPrepare=true;
            isPause=true;
            isPlaying=false;
            Log.d(TAG, "pauseMediaPlayer: ");
            try  {
                mediaPlayer.pause();
            } catch (IllegalStateException e) {
            }
        }
    }

    public void stopMediaPlayer() {
        if(!isStop){
            Log.d(TAG, "stopMediaPlayer: ");
            try {
                mediaPlayer.stop();
                mediaPlayer.reset();
            } catch (IllegalStateException e) {
            }
            isStop=true;
            isPrepare=false;
            isPlaying=false;
        }
    }

    public void releaseMediaPlayer(){
        mediaPlayer.release();
    }


    private interface SeekBarProgressListener{
        void getProgress(int currentPosition,int totalPosition);
    }
    public void setProgressListener(SeekBarProgressListener listener){
        this.listener = listener;
    }

    private void setSeek(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        if(isPlaying){
                            currentPosition = mediaPlayer.getCurrentPosition();
                            if (listener != null) {
                                listener.getProgress(currentPosition, totalPosition);
                            }
                            currentTv.post(new Runnable() {
                                @Override
                                public void run() {
                                    currentTv.setText(TimeFormat(currentPosition));
                                }
                            });
                            seekBar.setProgress(currentPosition);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if(mediaPlayer!=null){
                mediaPlayer.seekTo(seekBar.getProgress());
                isPlaying=true;
            }
        }
    };

    private String TimeFormat(int time){
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        time = Math.abs(time);
        String ms = formatter.format(time);
        return ms;
    }

    public FMediaPlayer setVideoPreView(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(url,new HashMap<String, String>());
                final Bitmap bitmap = mmr.getFrameAtTime();
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            Drawable drawable = new BitmapDrawable(bitmap);
                            surfaceView.setBackground(drawable);
                        }
                    }
                });
                mmr.release();
            }
        }).start();
        return this;
    }

    public static void setImageViewFace(final Context context1, final ImageView imageView, final String url){
        final String filename = MD5Util.StringToMD5(url);
        final Bitmap mbitmap = getBitmapFromLocal(filename);
        if(mbitmap!=null){
            Log.d("getMediaImg", "FromDisk:");
            ((Activity)context1).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Drawable drawable = new BitmapDrawable(mbitmap);
                        imageView.setImageDrawable(drawable);
                    }
                }
            });
        }else{
            Log.d("getMediaImg", "FromNet");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
                    metadataRetriever.setDataSource(url,new HashMap<String, String>());
                    final Bitmap bitmap = metadataRetriever.getFrameAtTime();
                    saveBitmapToLocal(filename,bitmap);

                    ((Activity)context1).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                Drawable drawable = new BitmapDrawable(bitmap);
                                imageView.setBackground(drawable);
                            }
                        }
                    });
                    metadataRetriever.release();
                }
            }).start();
        }
    }

    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache/pics";
    /**
     * 向本地SD卡写网络图片
     *
     * @param bitmap
     */
    private static void saveBitmapToLocal(String fileName, Bitmap bitmap) {
        try {
            // 创建文件流，指向该路径，文件名叫做fileName
            File file = new File(FILE_PATH, fileName);
            // file其实是图片，它的父级File是文件夹，判断一下文件夹是否存在，如果不存在，创建文件夹
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                // 文件夹不存在
                fileParent.mkdirs();// 创建文件夹
            }
            // 将图片保存到本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从本地SD卡获取缓存的bitmap
     */
    private static Bitmap getBitmapFromLocal(String fileName) {
        try {
            File file = new File(FILE_PATH, fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
                        file));
                return bitmap;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private MediaPlayer.OnVideoSizeChangedListener sizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            surfaceView.adjustSize(mp.getVideoWidth(),mp.getVideoHeight());
        }
    };
    private class MediaPlayerListener implements MediaPlayer.OnPreparedListener
            ,MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener{

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(context, "播放完成",
                    Toast.LENGTH_SHORT).show();
            mediaPlayer.stop();
            mediaPlayer.release();
            ((Activity)context).finish();
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
//            Toast.makeText(context, "onPrepared",
//                    Toast.LENGTH_SHORT).show();
            surfaceView.setBackgroundResource(0);
            isPrepare=true;
            mediaPlayer.start();
            progressBar.setVisibility(View.GONE);
            totalPosition = mediaPlayer.getDuration();
            seekBar.setMax(totalPosition);
            totalTv.setText(TimeFormat(totalPosition));
            Log.d(TAG, "onPrepared: "+totalPosition);
            if(!isSetSeek){
                setSeek();
                isSetSeek=true;
            }
        }

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Log.d(TAG, "onError: "+what);
            return false;
        }
    }
}
