package com.zhy.example;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.zlib.view.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TwoFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.topbar)
    TopBar topbar;

    @Override
    public void initView() {

    }

    @Override
    public View contentView(Bundle savedInstanceState) {
        return getView(R.layout.fragment_two);
    }
}
