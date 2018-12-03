package com.example.administrator.douyin;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class FullWindowVideoView extends VideoView {

    public FullWindowVideoView(Context context) {
        super(context);
    }

    public FullWindowVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullWindowVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 其实就是在这里做了一些处理。
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
