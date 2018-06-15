package util.MediaLoader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by mac on 2018/5/12.
 */

public class VideoSurfaceView extends SurfaceView {

    private int videoWidth;
    private int videoHeight;

    public VideoSurfaceView(Context context) {
        super(context);
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        videoWidth = 0;
        videoHeight = 0;
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(videoWidth,widthMeasureSpec);
        int height = getDefaultSize(videoHeight,heightMeasureSpec);
        if(videoHeight>0&&videoWidth>0){
            int widthSpecMode  = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            if(widthSpecMode==MeasureSpec.EXACTLY && heightSpecMode==MeasureSpec.EXACTLY){
                //如果是固定值或者match_parent
                width = widthSpecSize;
                height = heightSpecSize;

                //做适配 不让视频拉伸 保持原来比例
                if(videoWidth * height < width * videoWidth){
                    width = height * videoWidth / videoHeight;
                }else{
                    height = width * videoHeight / videoWidth;
                }
            }else if(widthSpecMode == MeasureSpec.EXACTLY){
                //width 为确定值  height为wrap
                width = widthSpecSize;
                // 计算高多少，保持原来宽高的比例
                height = width * videoHeight / videoWidth;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    height = heightSpecSize;
                }
            }else if(heightSpecMode == MeasureSpec.EXACTLY){
                height = heightSpecSize;
                // 计算宽多少，保持原来宽高的比例
                width = height * videoWidth / videoHeight;
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    width = widthSpecSize;
                }
            }else {
                width = videoWidth;
                height = videoHeight;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    height = heightSpecSize;
                    width = height * videoWidth / videoHeight;
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    width = widthSpecSize;
                    height = width * videoHeight / videoWidth;
                }
            }
        }
        setMeasuredDimension(width,height);
    }

    public void adjustSize(int videoWidth,int videoHeight){
        if (videoWidth == 0 || videoHeight == 0) return;
        this.videoWidth = videoWidth;
        this.videoHeight = videoHeight;
        // 设置Holder固定的大小
        getHolder().setFixedSize(videoWidth, videoHeight);
        // 重新设置自己的大小
        requestLayout();
    }















}
