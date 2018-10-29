package com.zhy.zlib.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.zlib.utils.ImageUtils;

import java.util.List;

/**
 * 万能适配器
 * 适用于 ListView GridView RecyclerView等
 * Created by YangYang on 2015/7/27.
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;
    private SparseArray<View> views;

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
        this.views = new SparseArray<>();
    }

    @Override
    public int getCount() {
        if (mDatas == null)
            return 0;
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        if (mDatas == null)
            return null;
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = views.get(position);
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position));
        views.put(position, convertView);
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder h, T i);

    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

    public static class ViewHolder {
        private SparseArray<View> mViews;
        private int mPosition;
        private View mConvertView;
        private ViewGroup parent;
        private Context context;

        private ViewHolder(Context context, ViewGroup parent, int layoutId,
                           int position) {
            this.parent = parent;
            this.context = context;
            this.mPosition = position;
            this.mViews = new SparseArray<>();
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                    false);
            // setTag
            mConvertView.setTag(this);
        }

        public ViewHolder(View view) {
            this.context = view.getContext();
            this.mViews = new SparseArray<>();
            mConvertView = view;
            // setTag
            mConvertView.setTag(this);
        }

        public int getmPosition() {
            return mPosition;
        }

        /**
         * 拿到一个ViewHolder对象
         *
         * @param context
         * @param convertView
         * @param parent
         * @param layoutId
         * @param position
         * @return
         */
        public static ViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId, int position) {
            if (convertView == null) {
                return new ViewHolder(context, parent, layoutId, position);
            }
            return (ViewHolder) convertView.getTag();
        }

        public void changeLayout(int layoutId) {
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                    false);
        }

        public View getConvertView() {
            return mConvertView;
        }

        /**
         * 通过控件的Id获取对于的控件，如果没有则加入views
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 为TextView设置字符串
         *
         * @param viewId
         * @param text
         * @return
         */
        public ViewHolder setText(int viewId, String text) {
            TextView view = getView(viewId);
            view.setText(text);
            return this;

        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @param drawableId
         * @return
         */
        public ViewHolder setImgRes(int viewId, int drawableId) {
            ImageView view = getView(viewId);
            view.setImageResource(drawableId);
            return this;
        }

        /**
         * 为View设置背景图片
         *
         * @param viewId
         * @param drawableId
         * @return
         */
        public ViewHolder setBgRes(int viewId, int drawableId) {
            View view = getView(viewId);
            view.setBackgroundResource(drawableId);
            return this;
        }

        /**
         * 显示隐藏view
         *
         * @param viewId
         * @param visibility
         * @return
         */
        public ViewHolder setVisibility(int viewId, int visibility) {
            View view = getView(viewId);
            view.setVisibility(visibility);
            return this;
        }

        /**
         * 给view设置点击事件
         *
         * @param viewId
         * @param listener
         * @return
         */
        public ViewHolder setClick(int viewId, View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
            return this;
        }

        /**
         * 给整项设置点击事件
         *
         * @param listener
         * @return
         */
        public ViewHolder setClick(View.OnClickListener listener) {
            mConvertView.setOnClickListener(listener);
            return this;
        }

        /**
         * 给整项设置长按事件
         *
         * @param listener
         * @return
         */
        public ViewHolder setLongClick(View.OnLongClickListener listener) {
            mConvertView.setOnLongClickListener(listener);
            return this;
        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @param bm
         * @return
         */
        public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
            ImageView view = getView(viewId);
            view.setImageBitmap(bm);
            return this;
        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @param url
         * @return
         */
        public ViewHolder setImageByUrl(int viewId, Object url) {
            if (url != null)
//                if (getView(viewId).getClass().equals(ImageViewCard.class)) {
//                    ImageViewCard imageViewCard = getView(viewId);
//                    imageViewCard.loadImage(url);
//                } else {
                    ImageUtils.loadImage(context, url, (ImageView) getView(viewId));
//                }


            return this;
        }

        public ViewHolder setImageResize(int viewId, Object url, int w, int h) {
            if (url != null)
//                if (getView(viewId).getClass().equals(ImageViewCard.class)) {
//                    ImageViewCard imageViewCard = getView(viewId);
//                    imageViewCard.loadImageResize(url, w, h);
//                } else {
                    ImageUtils.loadImageResize(context, url, (ImageView) getView(viewId), w, h);
//                }


            return this;
        }

        /**
         * 设置图片 带默认图
         *
         * @param viewId
         * @param url
         * @param difImgRes
         * @return
         */
        public ViewHolder setImageByUrl(int viewId, String url, int difImgRes) {
            if (!TextUtils.isEmpty(url)) {
                ImageUtils.loadImage(context, url, difImgRes, (ImageView) getView(viewId));
            }
            return this;
        }

//        public void setIncludTextColor(View textView, String keyWord, int colorOxff) {
//            Utils.includTextColor((TextView) textView, keyWord, colorOxff);
//        }


        /**
         * 设置字体
         *
         * @param viewId 需要设置字体的TextView id
         * @param tf     字体
         * @return
         */
        public TextView setTypeFace(int viewId, Typeface tf) {
            TextView tv = getView(viewId);
            tv.setTypeface(tf);
            return tv;
        }

    }
}