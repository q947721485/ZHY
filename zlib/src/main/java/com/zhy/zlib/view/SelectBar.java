package com.zhy.zlib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.zlib.Base.LibConfig;
import com.zhy.zlib.R;
import com.zhy.zlib.utils.SelecteUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 邮件: ZhouYangGaoGao@163.com
 * 由 周洋 创建于 2016/10/29 下午5:24
 * 用途 :
 */
public class SelectBar extends LinearLayout {
    int id = 0xabcdef;
    public SelecteUtil slu;
    private Context mContext;
    LayoutParams itemlp, textlp, linelp, llp;
    List<TextView> tvs = new ArrayList<>();
    List<ImageView> lines = new ArrayList<>();

    public SelectBar(Context context) {
        this(context, null);
    }

    public SelectBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (getBackground() == null)
            setBackgroundColor(LibConfig.colorTheme);
        mContext = context;
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.SelectBar);
        String str = t.getString(R.styleable.SelectBar_Text);
        Drawable line;
        float tsUn, ts;
        int tc, tcUn;
        boolean isLongLine, isBottomTab;

        line = t.getDrawable(R.styleable.SelectBar_LineSrc);
        ts = t.getDimension(R.styleable.SelectBar_Textsize, sp2px(14));//默认已选文字大小
        tsUn = t.getDimension(R.styleable.SelectBar_TextsizeUn, sp2px(14));//默认未选文字大小
        tc = t.getColor(R.styleable.SelectBar_Textcolor, 0xffffffff);
        tcUn = t.getColor(R.styleable.SelectBar_TextcolorUn, 0xff616161);
        isLongLine = t.getBoolean(R.styleable.SelectBar_LongLine, false);
        isBottomTab = t.getBoolean(R.styleable.SelectBar_isBottomTab, false);
        t.recycle();


        if (str != null && !str.equals("")) {
            String[] tt = str.split("\\|");

            for (int i = 0; i < tt.length; i++) {
                if (i == 0) addText(tt[0], ts, tc, line, true, isLongLine, isBottomTab);
                else addText(tt[i], ts, tcUn, line, false, isLongLine, isBottomTab);
            }
        }
        if (getBackground() == null)
            setBackgroundColor(0xff0d0d0d);

        if (isBottomTab)
            setSelect(tc, tcUn, ts, tsUn, isBottomTab);
        else setSelect(tc, tcUn, ts, tsUn);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void setSelect(int selectColor, int selectUnColor, float ts, float tsUn) {
        View[] views = new View[tvs.size() * 2];
        if (tvs.size() > 0)
            tvs.get(0).getPaint().setFakeBoldText(true);
        for (int i = 0; i < tvs.size(); i++) {
            views[i] = tvs.get(i);
        }
        for (int j = 0; j < lines.size(); j++) {
            views[j + tvs.size()] = lines.get(j);
        }
        slu = new SelecteUtil(SelecteUtil.TEXT_LINE, new SelecteUtil.Onselecte() {
            @Override
            public boolean onselected(View v, int index) {
                if (listener != null) {
                    listener.onselected(v, index);
                }
                return true;
            }
        }, views).setSelectedClo(selectColor).setUnSelectedClo(selectUnColor).setSelectedTs(ts, tsUn);
    }

    public void setSelect(int selectColor, int selectUnColor, float ts, float tsUn, boolean isBottomTab) {
        View[] views = new View[tvs.size() * 2];
        if (tvs.size() > 0)
            tvs.get(0).getPaint().setFakeBoldText(true);
        for (int i = 0; i < tvs.size(); i++) {
            views[i] = tvs.get(i);
        }
        for (int j = 0; j < lines.size(); j++) {
            views[j + tvs.size()] = lines.get(j);
        }
        slu = new SelecteUtil(SelecteUtil.TEXT_IMG, new SelecteUtil.Onselecte() {
            @Override
            public boolean onselected(View v, int index) {
                if (listener != null) {
                    listener.onselected(v, index);
                }
                return true;
            }
        }, views).setSelectedClo(selectColor).setUnSelectedClo(selectUnColor).setSelectedTs(ts, tsUn);
    }

    private SelecteUtil.Onselecte listener;

    public SelecteUtil setOnselecte(SelecteUtil.Onselecte listener) {
        this.listener = listener;
        return slu;
    }


    private int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public SelectBar addText(String text, float ts, int tc, Drawable line, boolean visiable, boolean isLongLine, boolean isBottomTab) {
        if (text != null) {
            LinearLayout ll = new LinearLayout(mContext);
            LinearLayout item = new LinearLayout(mContext);
            item.setGravity(Gravity.CENTER);
            ll.setGravity(Gravity.CENTER);
            ll.setOrientation(VERTICAL);
            TextView tv = new TextView(mContext);
            tv.setSingleLine();
            tv.setText(text);
            tv.setId(++id);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, ts);
            tv.setTextColor(tc);
            ImageView img = new ImageView(mContext);
            img.setId(id++);


            if (isBottomTab) {
                linelp = new LayoutParams(dip2px(22), dip2px(22));
                linelp.setMargins(0, 5, 0, 0);
                itemlp = new LayoutParams(0, sp2px(44), 1);
                llp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                textlp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                textlp.setMargins(dip2px(5), 0, dip2px(5), 0);
                ll.addView(img, linelp);
                ll.addView(tv, textlp);
            } else {


                if (line != null) {
                    img.setBackground(line);
                } else {
                    img.setBackgroundResource(R.color.colorBlue);
                }
                if (!visiable)
                    img.setVisibility(INVISIBLE);
                if (llp == null) {
                    linelp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(2));
                    if (isLongLine) {
                        itemlp = new LayoutParams(0, sp2px(44), 1);
                        llp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        textlp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                        textlp.setMargins(dip2px(5), 0, dip2px(5), 0);
                    } else {
                        itemlp = new LayoutParams(0, sp2px(44), 1);
                        llp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        textlp = new LayoutParams(sp2px(56), ViewGroup.LayoutParams.MATCH_PARENT, 1);
                    }
                }
                ll.addView(tv, textlp);
                ll.addView(img, linelp);
            }


            item.addView(ll, llp);
            addView(item, itemlp);
            tvs.add(tv);
            lines.add(img);
            return this;
        }
        return null;
    }

    public ImageView dot;

    public List<TextView> getTvs() {
        return tvs;
    }

    public SelecteUtil withViewPager(View viewpager, FragmentManager fmg, final Fragment... fragments) {
        slu.withViewPager(viewpager, fmg, fragments);
        return slu;
    }

    public Fragment[] getFragments() {
        return slu.getFragments();
    }

}
