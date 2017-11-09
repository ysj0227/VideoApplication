package com.video.library1711;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by shijie.yang on 2017/11/9.
 */

public abstract class MyAndroidActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutActivityId());
        initView();
    }

    protected abstract int layoutActivityId();
    protected abstract void initView();
}
