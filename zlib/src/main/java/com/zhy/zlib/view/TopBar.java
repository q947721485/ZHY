package com.zhy.zlib.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.zlib.Base.LibConfig;
import com.zhy.zlib.R;
import com.zhy.zlib.listener.TopListener;
import com.zhy.zlib.utils.SelecteUtil;


/**
 * 邮件: ZhouYangGaoGao@163.com
 * 由 周洋 创建于 2016/10/26 下午3:07
 * 用途 :通用topbar
 */
public class TopBar extends RelativeLayout {
    private SelectBar selectBar;
    public ImageView L1, L2, R1, R2;
    public TextView LT, CT, RT;
    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(final Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LinearLayout oldBar = new LinearLayout(context);
        if (getBackground() == null)
            setBackgroundColor(LibConfig.colorTheme);
        final Drawable l1, l2, r1, r2, c, selectTline;
        float ts, iw, selectts, selecttsUn;
        LinearLayout.LayoutParams imglp, tvlp, ctvlp;
        int tc, selecttc, selecttcun;
        String lt, rt, ct;
        final boolean isLongLine, isFinish;
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.TopBar);
        selectTline = t.getDrawable(R.styleable.TopBar_SelectTLineSrc);
        selectts = t.getDimension(R.styleable.TopBar_SelectTsize, sp2px(17));//默认已选文字大小
        selecttsUn = t.getDimension(R.styleable.TopBar_SelectTsizeUn, sp2px(15));//默认未选文字大小
        selecttc = t.getColor(R.styleable.TopBar_SelectTcolor, 0xffffffff);
        selecttcun = t.getColor(R.styleable.TopBar_SelectTcolorUn, 0x80ffffff);
        l1 = t.getDrawable(R.styleable.TopBar_L1src);
        l2 = t.getDrawable(R.styleable.TopBar_L2src);
        c = t.getDrawable(R.styleable.TopBar_CSrc);
        r1 = t.getDrawable(R.styleable.TopBar_R1src);
        r2 = t.getDrawable(R.styleable.TopBar_R2src);
        lt = t.getString(R.styleable.TopBar_LT);
        rt = t.getString(R.styleable.TopBar_RT);
        ct = t.getString(R.styleable.TopBar_CT);
        ts = t.getDimension(R.styleable.TopBar_Tsize, sp2px(17));
        iw = t.getDimension(R.styleable.TopBar_ImgWidth, dip2px(24));
        tc = t.getColor(R.styleable.TopBar_Tcolor, 0xffffffff);
        isLongLine = t.getBoolean(R.styleable.TopBar_SelectLongLine, false);
        isFinish = t.getBoolean(R.styleable.TopBar_isFinish, false);
        t.recycle();

        final LinearLayout left, leftClick, right, center;

        ctvlp = new LinearLayout.LayoutParams(0, dip2px(48), 1);
        tvlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dip2px(48));
        tvlp.setMargins(dip2px(6), 0, dip2px(6), 0);
        imglp = new LinearLayout.LayoutParams((int) iw, (int) iw);
        imglp.setMargins(dip2px(9), 0, dip2px(9), 0);
        left = new LinearLayout(context);
        leftClick = new LinearLayout(context);
        left.setPadding(dip2px(6), 0, 0, 0);
        left.setGravity(Gravity.START + Gravity.CENTER_VERTICAL);
        right = new LinearLayout(context);
        right.setGravity(Gravity.END + Gravity.CENTER_VERTICAL);
        right.setPadding(0, 0, dip2px(6), 0);
        center = new LinearLayout(context);
        center.setGravity(Gravity.CENTER);


        if (l1 != null) {
            L1 = new ImageView(context);
            L1.setImageDrawable(l1);
            left.addView(L1, imglp);
            L1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.l1Click();
                }
            });
        } else if (isFinish) {
            L1 = new ImageView(context);
            L1.setImageResource(R.drawable.back);
            left.addView(L1, imglp);
            L1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();

                }
            });
        }
        if (lt != null) {
            String ltt[] = lt.split("\\|");
            if (ltt.length > 1) {
                selectBar = new SelectBar(context);
                for (int i = 0; i < ltt.length; i++) {
                    if (i == 0)
                        selectBar.addText(ltt[0], selectts, selecttc, selectTline, true, isLongLine, false);
                    else
                        selectBar.addText(ltt[i], selecttsUn, selecttcun, selectTline, false, isLongLine, false);
                }
                selectBar.setSelect(selecttc, selecttcun, selectts, selecttsUn);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, dip2px(47), 1);
                lp.setMargins(dip2px(6), 0, dip2px(6), 0);
                left.addView(selectBar, lp);
            } else {
                LT = new TextView(context);
                LT.setText(lt);
                LT.setSingleLine();
                LT.setGravity(Gravity.CENTER);
                LT.setTextSize(TypedValue.COMPLEX_UNIT_PX, ts);
                LT.setTextColor(tc);
                left.addView(LT, tvlp);
                LT.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isFinish) {
                            ((Activity) context).finish();
                        } else if (listener != null)
                            listener.lTClick();
                    }
                });
            }
        }
        if (l2 != null) {
            L2 = new ImageView(context);
            L2.setImageDrawable(l2);
            left.addView(L2, imglp);
            L2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.l2Click();
                }
            });
        }

        if (r2 != null) {
            R2 = new ImageView(context);
            R2.setImageDrawable(r2);
            right.addView(R2, imglp);
            R2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.r2Click();
                }
            });
        }
        if (rt != null) {
            String rtt[] = rt.split("\\|");
            if (rtt.length > 1) {
                selectBar = new SelectBar(context);
                for (int i = 0; i < rtt.length; i++) {
                    if (i == 0)
                        selectBar.addText(rtt[0], selectts, selecttc, selectTline, true, isLongLine, false);
                    else
                        selectBar.addText(rtt[i], selectts, selecttcun, selectTline, false, isLongLine, false);
                }
                selectBar.setSelect(selecttc, selecttcun, selectts, selecttsUn);
                right.addView(selectBar, new LinearLayout.LayoutParams(0, dip2px(47), 1));
            } else {
                RT = new TextView(context);
                RT.setText(rt);
                RT.setGravity(Gravity.CENTER);
                RT.setTextSize(TypedValue.COMPLEX_UNIT_PX, ts);
                right.addView(RT, tvlp);
                RT.setTextColor(tc);
                RT.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null)
                            listener.rTClick();
                    }
                });
            }
        }
        if (r1 != null) {
            R1 = new ImageView(context);
            R1.setImageDrawable(r1);
            right.addView(R1, imglp);
            R1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.r1Click();
                }
            });
        }
        if (c != null) {
            ImageView C = new ImageView(context);
            C.setImageDrawable(c);
            center.addView(C, imglp);
            C.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.cClick();
                }
            });

        }


        if (ct != null) {
            String tt[] = ct.split("\\|");
            if (tt.length > 1) {
                selectBar = new SelectBar(context);
                for (int i = 0; i < tt.length; i++) {
                    if (i == 0)
                        selectBar.addText(tt[0], selectts, selecttc, selectTline, true, isLongLine, false);
                    else
                        selectBar.addText(tt[i], selectts, selecttcun, selectTline, false, isLongLine, false);
                }
                selectBar.setSelect(selecttc, selecttcun, selectts, selecttsUn);
                center.addView(selectBar, ctvlp);
            } else {
                CT = new TextView(context);
                CT.setText(ct);
                CT.setGravity(Gravity.CENTER);
                CT.setTextColor(tc);
                CT.setTextSize(TypedValue.COMPLEX_UNIT_PX, ts);
                center.addView(CT, tvlp);
                CT.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null)
                            listener.tClick();
                    }
                });
            }
        }
        if (selectBar != null)
            selectBar.setBackgroundColor(0x00000000);
        oldBar.setGravity(Gravity.CENTER_VERTICAL);

        leftClick.addView(left, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dip2px(48)));

        int leftCount = left.getChildCount(), centerCount = center.getChildCount(), rightCount = right.getChildCount();


        if (leftCount > 0 && centerCount == 0 && rightCount == 0) {
            oldBar.addView(leftClick, ctvlp);
        }
        if (leftCount >= 0 && centerCount > 0) {
            oldBar.addView(leftClick, ctvlp);
            oldBar.addView(center, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dip2px(48)));
            oldBar.addView(right, ctvlp);
        }

        if (leftCount >= 0 && centerCount == 0 && rightCount > 0) {
            oldBar.addView(leftClick, ctvlp);
            oldBar.addView(right, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dip2px(48)));
        }

        addView(oldBar, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(48)));
    }

    private TopListener listener;

    //设置点击事件监听器
    public void setOnTopListener(TopListener listener) {
        this.listener = listener;
    }


    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public void setTextSize(TextView textView, float textsize) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(textsize));
    }

    public void setSelectListener(SelecteUtil.Onselecte selectListener){
        if (selectBar!=null){
            selectBar.setOnselecte(selectListener);
        }
    }

}


