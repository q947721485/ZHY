package com.zhy.zlib.listener;

import android.view.View;

/**
 * Created by YangYang on 2017/9/8.
 */

public interface AddView<T> {
    View initView(int index, T data);
}
