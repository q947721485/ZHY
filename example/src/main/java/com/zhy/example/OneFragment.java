package com.zhy.example;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhy.zlib.utils.SelecteUtil;
import com.zhy.zlib.view.TopBar;

import butterknife.BindView;

public class OneFragment extends BaseFragment {
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.topbar)
    TopBar topbar;

    @Override
    public void initView() {
        topbar.setSelectListener(new SelecteUtil.Onselecte() {
            @Override
            public boolean onselected(View v, int index) {
                switch (index) {
                    case 0:
                        text.setBackgroundColor(0xffff0000);
                        break;
                    case 1:
                        text.setBackgroundColor(0xff00ff00);
                        break;
                    case 2:
                        text.setBackgroundColor(0xff0000ff);
                        break;
                }

                return false;
            }
        });
    }

    @Override
    public View contentView(Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_one, null);
    }

}
