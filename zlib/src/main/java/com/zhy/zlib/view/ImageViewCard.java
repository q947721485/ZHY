package com.zhy.zlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zhy.zlib.R;
import com.zhy.zlib.utils.ImageUtils;


/**
 * 图片卡  就是加圆角和阴影用的 用来替换RoundAngleImageView的(那个太容易内存溢出)
 * Created by YangYang on 2017/11/7.
 */

public class ImageViewCard extends CardView {
    ImageView imageView;

    public ImageViewCard(Context context) {
        this(context, null);
    }

    public ImageViewCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.ImageViewCard);
        Drawable drawable = t.getDrawable(R.styleable.ImageViewCard_src);
        t.recycle();
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
        addView(imageView);
        setCardBackgroundColor(0x00000000);
        if (getRadius() == 7.0f) {
            setRadius(14.0f);
        }
    }

    public void loadImage(Object model) {
        ImageUtils.loadImage(imageView.getContext(), model, imageView);
    }

    public void loadImageResize(Object model, int w, int h) {
        ImageUtils.loadImageResize(imageView.getContext(), model, imageView,w,h);
    }

    public ImageView getImageView() {
        return imageView;
    }

}
