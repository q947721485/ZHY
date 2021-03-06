package com.zhy.zlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.widget.ImageView;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mufaith on 2017/8/17.
 */

public class ImageUtils {

    /**
     * 加载静图
     *
     * @param context    当前activity或者fragment
     * @param model      加载的具体数据格式 如 Url,SD卡资源文件,Resource资源文件
     * @param resourceId 默认占位图资源id
     * @param imageView  加载图片的ImageView
     */
    public static void loadImage(Context context, @NonNull Object model, int resourceId, ImageView imageView) {

        GlideApp.with(context).load(model).placeholder(resourceId).centerCrop()
                .into(imageView);

    }


    /**
     * 加载静图(无占位图)
     *
     * @param context   当前activity或者fragment
     * @param model     加载的具体数据格式 如 Url,SD卡资源文件,Resource资源文件
     * @param imageView 加载图片的ImageView
     */
    public static void loadImage(Context context, @NonNull Object model, ImageView imageView) {

        GlideApp.with(context).load(model).thumbnail(0.5f).centerCrop()
                .into(imageView);

    }


    public static float fold = 1;

    public static void loadImageResize(Context context, @NonNull Object model, ImageView imageView, int w, int h) {

        GlideApp.with(context).load(model).override((int) (w * fold), (int) (h * fold)).thumbnail(0.5f).centerCrop()
                .into(imageView);

    }

    /**
     * 加载静图(无占位图)
     *
     * @param context   当前activity或者fragment
     * @param model     加载的具体数据格式 如 Url,SD卡资源文件,Resource资源文件
     * @param imageView 加载图片的ImageView
     */
    public static void loadImageFit(Context context, @NonNull Object model, ImageView imageView) {

        GlideApp.with(context).load(model).fitCenter()
                .into(imageView);

    }


    /**
     * @param context    当前activity或者fragment
     * @param model      加载的具体数据格式 如 Url,SD卡资源文件,Resource资源文件
     * @param resourceId 默认占位图资源id
     * @param imageView  加载图片的ImageView
     */
    public static void loadGifImage(Context context, @NonNull Object model, int resourceId, ImageView imageView) {
        GlideApp.with(context).asGif().load(model).placeholder(resourceId).centerCrop()
                .into(imageView);
    }

    /**
     * 获取bitmap
     *
     * @param file      文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(File file, int maxWidth, int maxHeight) {
        if (file == null) return null;
        InputStream is = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            is = new BufferedInputStream(new FileInputStream(file));
            BitmapFactory.decodeStream(is, null, options);
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(is, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 计算采样大小
     *
     * @param options   选项
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样大小
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        if (maxWidth == 0 || maxHeight == 0) return 1;
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while ((height >>= 1) >= maxHeight && (width >>= 1) >= maxWidth) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

}
