package com.zhy.zlib.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.zlib.R;
import com.zhy.zlib.adapter.MyPagerAdapter;
import com.zhy.zlib.listener.AddView;
import com.zhy.zlib.listener.ClickListener;
import com.zhy.zlib.view.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * 邮件: ZhouYangGaoGao@163.com
 * 由 周洋 创建于 16/9/19
 * 用途 : 无限循环的viewpager 和文字单选封装
 */
public class SelecteUtil implements View.OnClickListener, ViewPager.OnPageChangeListener {
    public final static int ONLY_TEXT = 0, TEXT_TEXT = 1, TEXT_LINE = 2, IMAGEVIEW = 3, IMG_LINE = 4, TEXT_IMG = 5;
    private List<TextView> tvs = new ArrayList<>(), tvs2 = new ArrayList<>();
    private List<View> lines = new ArrayList<>(), pagerViews;// 下划线  viewpager要显示的view
    private List<ImageView> imgs = new ArrayList<>();//图片集合
    private ViewPager viewPager;
    private AutoScrollViewPager mAutoScrollViewPager;
    private LinearLayout dotLayout;
    private MyPagerAdapter adapter;
    private View[] mViews;
    private List<Integer> imgIdsSelected, imgIdsUnSelected;
    private Context context;
    public Onselecte onselecte;//选择回调
    private int cloSelected = 0xff5B5EE4, cloUnSelected = 0xffa3a3a3;//选择时的颜色   未选择的颜色
    private float textSize = 0, textSizeUn = 0;//选择时的大小   未选择的大小

    public SelecteUtil() {

    }

    public SelecteUtil(View autoScrollViewPager, Object datas, AddView addview, View dotLayout) {
        startAutoScrollViewPager(autoScrollViewPager, datas, addview, dotLayout);
    }


    int[] unIndex;
    String[] toastTexts;

    /**
     * 设置不可点击的项目
     *
     * @param indexs
     * @return
     */
    public SelecteUtil setUnClick(ClickListener unClickc, int... indexs) {
        unIndex = indexs;
        this.unClick = unClickc;
        return this;
    }

    private ClickListener unClick;

    /**
     * 设置不可点击的项目
     *
     * @param toastTexts 不可点击对应吐司文字
     * @return
     */
    public SelecteUtil setUnClick(String... toastTexts) {
        this.toastTexts = toastTexts;
        return this;
    }


    /**
     * 根据下标更改文字颜色和下划线显示
     *
     * @param index
     */
    public void doSelecte(int index) {
        boolean isDoselecte = true;
        if (unIndex != null && unIndex.length > 0)
            for (int i = 0; i < unIndex.length; i++) {
                if (unIndex[i] == index) {
                    isDoselecte = false;
                    if (toastTexts != null && toastTexts.length >= i) {
//                        ToastUtils.showToast(context, toastTexts[i], ToastUtils.LENGTH_SHORT);
                    }
                    break;
                }
            }
        if (isDoselecte) {
            if (lines.size() != 0) {
                for (View line : lines) {
                    line.setVisibility(View.INVISIBLE);
                }
                lines.get(index).setVisibility(View.VISIBLE);
            }
            if (tvs.size() > 0) {
                for (TextView tv : tvs) {
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeUn == 0 ? sp2px(context, 11) : textSizeUn);
                    tv.setTextColor(cloUnSelected);
                    tvs.get(index).getPaint().setFakeBoldText(false);
                }
                tvs.get(index).setTextColor(cloSelected);
                tvs.get(index).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize == 0 ? sp2px(context, 11) : textSize);
                tvs.get(index).getPaint().setFakeBoldText(true);
            }
            if (tvs2.size() != 0) {
                for (TextView tv : tvs2) {
                    tv.setTextColor(cloUnSelected);
                }
                tvs2.get(index).setTextColor(cloSelected);
            }
            if (imgs.size() > 0&&imgIdsUnSelected!=null) {
                for (int i = 0; i < imgs.size(); i++) {
                    imgs.get(i).setImageResource(imgIdsUnSelected.get(i));
                }
                imgs.get(index).setImageResource(imgIdsSelected.get(index));
            }
            if (mViews != null)
                onselecte.onselected(mViews[index], index);
        }
        if (viewPager != null) {
            viewPager.setCurrentItem(index);
        }

    }

    Fragment[] fragments;

    /**
     * 文字与viewpager联动
     *
     * @param viewpager 直接传viewpager或者findviewbyid都可以
     * @param fmg       fragment管理器
     * @param fragments 传入要联动的所有fragment
     */
    public SelecteUtil withViewPager(View viewpager, FragmentManager fmg, final Fragment... fragments) {
        this.viewPager = (ViewPager) viewpager;
        viewPager.setOffscreenPageLimit(fragments.length);
        this.fragments = fragments;
        viewPager.setAdapter(new FragmentPagerAdapter(fmg) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

        });
        viewPager.addOnPageChangeListener(this);
        return this;
    }

    public Fragment[] getFragments() {
        return fragments;
    }

    /**
     * 文字与viewpager联动
     *
     * @param viewpager 直接传viewpager或者findviewbyid都可以
     * @param fmg       fragment管理器
     * @param fragments 传入要联动的所有fragment
     */
    public SelecteUtil(View viewpager, FragmentManager fmg, final Fragment... fragments) {
        withViewPager(viewpager, fmg, fragments);
    }

    public void selectePage(int index) {
        viewPager.setCurrentItem(index);
    }

    /**
     * 文字和图标选择器
     *
     * @param type      选择器类型 ONLY_TEXT: 文本 TEXT_TEXT: 双文本 TEXT_LINE: 文本下划线
     * @param onselecte 选择监听
     * @param views     前一半传TextView 后一半传view(必须)
     */
    public SelecteUtil(int type, Onselecte onselecte, View... views) {
        setTextSelect(type, onselecte, views);
    }

    /**
     * 文字和图标选择器
     *
     * @param type      选择器类型 ONLY_TEXT: 文本 TEXT_TEXT: 双文本 TEXT_LINE: 文本下划线
     * @param onselecte 选择监听
     * @param views     前一半传TextView 后一半传view(必须)
     */
    public SelecteUtil setTextSelect(int type, Onselecte onselecte, View... views) {
        this.onselecte = onselecte;
        mViews = views;
        if (views.length > 0) {
            this.context = views[0].getContext();
        }
        for (View v : views)
            v.setOnClickListener(this);

        switch (type) {

            case ONLY_TEXT:
                for (View tv : views)
                    tvs.add((TextView) tv);

                break;
            case IMAGEVIEW:
                for (View img : views)
                    imgs.add((ImageView) img);

                break;
            case IMG_LINE:
                for (int i = 0; i < views.length / 2; i++) {
                    imgs.add((ImageView) views[i]);
                    lines.add(views[i + views.length / 2]);
                }

                break;
            default:
                if (views.length > 0 && views.length % 2 == 0) {
                    for (int i = 0; i < views.length / 2; i++)
                        tvs.add((TextView) views[i]);

                    if (type == TEXT_TEXT) {
                        for (int i = views.length / 2; i < views.length; i++)
                            tvs2.add((TextView) views[i]);

                    } else if (type == TEXT_LINE) {
                        for (int i = views.length / 2; i < views.length; i++) {
                            lines.add(views[i]);
                        }
                    } else if (type == TEXT_IMG) {
                        for (int i = views.length / 2; i < views.length; i++) {
                            imgs.add((ImageView) views[i]);
                        }
                    }
                } else {
                    if (views.length > 0)
                        LogUtils.e("SelecteUtil-View... views 参数不正确");
                }
                break;
        }
        return this;
    }

    /**
     * 自动巡展的viewpager 封装了可添加只是图标和无限循环
     *
     * @param autoScrollViewPager viewpager
     * @param datas               数据集合
     * @param dotLayout           指示图标的容器layout 一般传Linerlayout
     * @return
     */
    public SelecteUtil startAutoScrollViewPager(View autoScrollViewPager, Object datas, AddView addview, View dotLayout) {
        this.addview = addview;
        pagerViews = new ArrayList<>();
        mAutoScrollViewPager = (AutoScrollViewPager) autoScrollViewPager;
        mAutoScrollViewPager.setOffscreenPageLimit(1);
        mAutoScrollViewPager.setPageMargin(30);
        mAutoScrollViewPager.setOffscreenPageLimit(3);
        this.dotLayout = (LinearLayout) dotLayout;
        initViewpager();
        mAutoScrollViewPager.addOnPageChangeListener(this);
        upDate(datas);
        mAutoScrollViewPager.startAutoScroll();
        return this;
    }

    private AddView addview;

//    public void upDate(Object datas) {
//        List<Object> mdatas = (List<Object>) datas;
//        if (mdatas.size() > 0) {
//            pagerViews.clear();
//            mdatas.add(0, mdatas.get(mdatas.size() - 1));
//            mdatas.add(mdatas.get(1));
//            for (int i = 0; i < mdatas.size(); i++) {
//                pagerViews.add(addview.initView(i, mdatas.get(i)));
//            }
//            if (mAutoScrollViewPager != null) {
//                adapter.notifyDataSetChanged();
//                mAutoScrollViewPager.setCurrentItem(1);
//            }
//            initDot(mAutoScrollViewPager.getContext());
//        }
//    }
    public void upDate(Object datas) {
        List<Object> mdatas = (List<Object>) datas;
        if (mdatas.size() > 0) {
            pagerViews.clear();
            if (mdatas.size()==1)
                mAutoScrollViewPager.setPageMargin(1000);
            mdatas.add(0, mdatas.get(mdatas.size() - 1));
            mdatas.add(mdatas.get(1));
            for (int i = 0; i < mdatas.size(); i++) {
                pagerViews.add(addview.initView(i, mdatas.get(i)));
            }
            if (mAutoScrollViewPager != null) {
                adapter.notifyDataSetChanged();
                mAutoScrollViewPager.setCurrentItem(1);
            }
            initDot(mAutoScrollViewPager.getContext());
        }
    }

    private int selectedDot = R.drawable.shape_dot_blue;

    public void setDotSelected(int resId) {
        selectedDot = resId;
    }

    /**
     * 初始化指示图标
     *
     * @param c 上下文
     */
    private void initDot(Context c) {
        if (pagerViews.size() > 3) {
            if (dotLayout != null) {
                dotLayout.setVisibility(View.VISIBLE);
                dotLayout.removeAllViews();
                int dotW = (int) (ScreenUtils.getScreenWidth(c) * 0.020);
                int dotH = (int) (ScreenUtils.getScreenWidth(c) * 0.020);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dotW, dotH);
                lp.setMargins(dotW / 2, 0, dotW / 2, 0);
                for (int i = 0; i < pagerViews.size() - 2; i++) {
                    ImageView img = new ImageView(c);
                    img.setScaleType(ImageView.ScaleType.FIT_XY);
                    img.setLayoutParams(lp);
                    if (i == 0) {
                        img.setImageResource(selectedDot);
                    } else {
                        img.setImageResource(R.drawable.shape_dot_d8d8d8);
                    }
                    dotLayout.addView(img);
                }
            }
        } else {
            dotLayout.setVisibility(View.INVISIBLE);
            mAutoScrollViewPager.stopAutoScroll();
        }
    }

    public void stop() {
        mAutoScrollViewPager.stopAutoScroll();
    }

    public void start() {
        mAutoScrollViewPager.startAutoScroll();
    }

    private void initViewpager() {
        adapter = new MyPagerAdapter(pagerViews);
        mAutoScrollViewPager.setAdapter(adapter);
        mAutoScrollViewPager.setOffscreenPageLimit(1);
        mAutoScrollViewPager.setOnPageChangeListener(this);
        mAutoScrollViewPager.setInterval(4000);// 设置播放间隔时间
        mAutoScrollViewPager.setAutoScrollDurationFactor(10);
        mAutoScrollViewPager.setCycle(true);// 设置是否循环
        ScreenUtils.setHight(mAutoScrollViewPager, 0.4555556);
        mAutoScrollViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mAutoScrollViewPager.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < (imgs.size() > 0 ? imgs.size() : tvs.size()); i++) {
            if ((imgs.size() != 0 && v.getId() == imgs.get(i).getId()) || (tvs.size() != 0 && v.getId() == tvs.get(i).getId()) || (tvs2.size() != 0 && v.getId() == tvs2.get(i).getId()) || (lines.size() != 0 && v.getId() == lines.get(i).getId())) {
                if (viewPager != null) {
                    viewPager.setCurrentItem(i);
                } else {
                    doSelecte(i);
                }
            }
        }
    }

    //设置选的后的文字颜色
    public SelecteUtil setSelectedClo(int cloSelected) {
        this.cloSelected = cloSelected;
        return this;
    }

    //设置未选择文字颜色
    public SelecteUtil setUnSelectedClo(int cloUnSelected) {
        this.cloUnSelected = cloUnSelected;
        return this;
    }

    //设置文字大小
    public SelecteUtil setSelectedTs(float textSize, float textSizeUn) {
        this.textSize = textSize;
        this.textSizeUn = textSizeUn;
        return this;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        if (viewPager != null) {
            doSelecte(position);
        } else {
            if (pagerViews.size() > 1) { //多于1，才会循环跳转
                if (position < 1) { //首位之前，跳转到末尾（N）
                    position = pagerViews.size() - 2;
                    mAutoScrollViewPager.setCurrentItem(position, false);
                } else if (position > pagerViews.size() - 2) { //末位之后，跳转到首位（1）
                    mAutoScrollViewPager.setCurrentItem(1, false); //false:不显示跳转过程的动画
                    position = 1;
                }
            }
            if (dotLayout != null) {
                for (int i = 0; i < dotLayout.getChildCount(); i++) {
                    ImageView img = (ImageView) dotLayout.getChildAt(i);
                    if (i == position - 1) {
                        img.setImageResource(selectedDot);
                    } else {
                        img.setImageResource(R.drawable.shape_dot_d8d8d8);
                    }
                }
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public AutoScrollViewPager getAVp() {
        return mAutoScrollViewPager;
    }

    public ViewPager getVp() {
        return viewPager;
    }


    public interface Onselecte {
        boolean onselected(View v, int index);
    }

    public SelecteUtil setImgResouce(int... ids) {
        imgIdsUnSelected = new ArrayList<>();
        imgIdsSelected = new ArrayList<>();
        if (ids.length % 2 == 0) {
            for (int i = 0; i < ids.length / 2; i++)
                imgIdsSelected.add(ids[i]);

            for (int i = ids.length / 2; i < ids.length; i++)
                imgIdsUnSelected.add(ids[i]);

        } else {
            LogUtils.e("SelecteUtil-ids... ids 参数不正确");
        }
        return this;
    }

    private int dip2px(Context c, float dipValue) {
        final float scale = c.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int sp2px(Context c, float spValue) {
        final float fontScale = c.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
