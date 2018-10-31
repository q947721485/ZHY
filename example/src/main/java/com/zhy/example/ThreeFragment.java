package com.zhy.example;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.zlib.adapter.CommonAdapter;
import com.zhy.zlib.listener.AddView;
import com.zhy.zlib.utils.SelecteUtil;
import com.zhy.zlib.view.AutoScrollViewPager;
import com.zhy.zlib.view.TopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ThreeFragment extends BaseFragment {
    @BindView(R.id.my_viewPager)
    AutoScrollViewPager myViewPager;
    @BindView(R.id.dot_layout)
    LinearLayout dotLayout;
    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.gv)
    GridView gv;
    SelecteUtil slu;
    List<String> gifs=new ArrayList<>();

    @Override
    public void initView() {
        List<String> datas = new ArrayList();
        datas.add("生");
        datas.add("日");
        datas.add("快");
        datas.add("乐");
        datas.add("!");
        slu = new SelecteUtil(myViewPager, datas, new AddView() {
            @Override
            public View initView(int index, Object data) {
                TextView textView = new TextView(getActivity());
                textView.setGravity(Gravity.CENTER);
                textView.setText(data.toString());
                textView.setBackgroundColor(0xff888888);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return textView;
            }
        }, dotLayout);

        gifs.add("BallPulseIndicator");
        gifs.add("BallGridPulseIndicator");
        gifs.add("BallClipRotateIndicator");
        gifs.add("BallClipRotatePulseIndicator");

        gifs.add("SquareSpinIndicator");
        gifs.add("BallClipRotateMultipleIndicator");
        gifs.add("BallPulseRiseIndicator");
        gifs.add("BallRotateIndicator");

        gifs.add("CubeTransitionIndicator");
        gifs.add("BallZigZagIndicator");
        gifs.add("BallZigZagDeflectIndicator");
        gifs.add("BallTrianglePathIndicator");

        gifs.add("BallScaleIndicator");
        gifs.add("LineScaleIndicator");
        gifs.add("LineScalePartyIndicator");
        gifs.add("BallScaleMultipleIndicator");

        gifs.add("BallPulseSyncIndicator");
        gifs.add("BallBeatIndicator");
        gifs.add("LineScalePulseOutIndicator");
        gifs.add("LineScalePulseOutRapidIndicator");

        gifs.add("BallScaleRippleIndicator");
        gifs.add("BallScaleRippleMultipleIndicator");
        gifs.add("BallSpinFadeLoaderIndicator");
        gifs.add("LineSpinFadeLoaderIndicator");

        gifs.add("TriangleSkewSpinIndicator");
        gifs.add("PacmanIndicator");
        gifs.add("BallGridBeatIndicator");
        gifs.add("SemiCircleSpinIndicator");

        gv.setAdapter(new CommonAdapter<String>(getActivity(),gifs,R.layout.item_gif) {
            @Override
            public void convert(ViewHolder h, String i) {
                AVLoadingIndicatorView av=h.getView(R.id.av);
                av.setIndicator(i);
//                av.show();
            }
        });


    }

    @Override
    public View contentView(Bundle savedInstanceState) {
        return getView(R.layout.fragment_three);
    }

    /**
     * Row 1
     *
     * BallPulseIndicator
     * BallGridPulseIndicator
     * BallClipRotateIndicator
     * BallClipRotatePulseIndicator
     * Row 2
     *
     * SquareSpinIndicator
     * BallClipRotateMultipleIndicator
     * BallPulseRiseIndicator
     * BallRotateIndicator
     * Row 3
     *
     * CubeTransitionIndicator
     * BallZigZagIndicator
     * BallZigZagDeflectIndicator
     * BallTrianglePathIndicator
     * Row 4
     *
     * BallScaleIndicator
     * LineScaleIndicator
     * LineScalePartyIndicator
     * BallScaleMultipleIndicator
     * Row 5
     *
     * BallPulseSyncIndicator
     * BallBeatIndicator
     * LineScalePulseOutIndicator
     * LineScalePulseOutRapidIndicator
     * Row 6
     *
     * BallScaleRippleIndicator
     * BallScaleRippleMultipleIndicator
     * BallSpinFadeLoaderIndicator
     * LineSpinFadeLoaderIndicator
     * Row 7
     *
     * TriangleSkewSpinIndicator
     * PacmanIndicator
     * BallGridBeatIndicator
     * SemiCircleSpinIndicator
     * Row 8
     *
     * com.wang.avi.sample.MyCustomIndicator
     */
}
