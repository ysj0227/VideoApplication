package com.video.library1711;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

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
    protected void setColor(TextView text,int n){
        text.setTextColor(n);
    }
    protected abstract int layoutActivityId();
    protected abstract void initView();

}
