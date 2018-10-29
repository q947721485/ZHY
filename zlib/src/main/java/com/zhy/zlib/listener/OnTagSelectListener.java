package com.zhy.zlib.listener;


import com.zhy.zlib.view.FlowTagLayout;

import java.util.List;


/**
 * Created by YangYang on 2017/7/27.
 */
public interface OnTagSelectListener {
    void onItemSelect(FlowTagLayout parent, List<Integer> selectedList);
}
