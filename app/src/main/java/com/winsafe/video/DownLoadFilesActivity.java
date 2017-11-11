package com.winsafe.video;

import android.view.View;
import android.widget.Button;

import com.video.library1711.MyAndroidActivity;
import com.winsafe.video.download.HttpDownloader;

/**
 * Created by shijie.yang on 2017/11/11.
 */

public class DownLoadFilesActivity extends MyAndroidActivity {
    @Override
    protected int layoutActivityId() {
        return R.layout.activity_down;
    }

    @Override
    protected void initView() {
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new MyThread(2).start();
            }
        });

    }
}
class MyThread extends Thread{

    private int flag = 0 ;

    public MyThread(){}

    public MyThread(int flag){
        this.flag = flag;
    }

    @Override
    public void run() {
        if ( 1 == flag ) { // 基本文件下载
            HttpDownloader httpDownloader = new HttpDownloader();
            String contents = httpDownloader.downloadBaseFile("http://192.168.1.104:8081/CFM_xfire/I_Remember.lrc");
            System.out.println(contents);
        }
        if ( 2 == flag ){ // 复杂文件下载及存储到手机SD卡
            HttpDownloader httpDownloader = new HttpDownloader();
            int resultInt = httpDownloader.downloadAllFile("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4","/downloadDir","/i_remember.mp4");
            System.out.println(resultInt);
        }
    }
}
