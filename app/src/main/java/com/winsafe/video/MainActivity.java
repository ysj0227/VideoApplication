package com.winsafe.video;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.video.library1711.MyAndroidActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MyAndroidActivity implements View.OnClickListener {
    //本地
//    String path = Environment.getExternalStorageDirectory() + "/sky.mp4";
    //网络
    String path = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    @BindView(R.id.textureView)
    TextureView textureView;
    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.pBar)
    ProgressBar pBar;

    @BindView(R.id.start)
    Button start;
    @BindView(R.id.pause)
    Button pause;
    @BindView(R.id.resume)
    Button resume;
    @BindView(R.id.down)
    Button down;

    private MediaController mediaController;


    @Override
    protected int layoutActivityId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);

        checkPermission();

    }

    //每0.5秒监听一次是否在播放视频
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            if (!videoView.isPlaying()) {
                pBar.setVisibility(View.VISIBLE);
            } else {
                pBar.setVisibility(View.GONE);
            }
            handler.postDelayed(runnable, 500);
        }
    };

    //初始化状态
    private void initMediaPlayer() {
        //网络
        String path = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        Uri uri = Uri.parse(path);
        videoView.requestFocus();
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pBar.setVisibility(View.GONE);
                mp.start();
                Log.i("TAG", "Prepared" + mp.isPlaying());
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                Log.i("TAG", "ERROR" + mp.isPlaying());
                if (!mp.isPlaying()) {
                    Dialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("提示")
                            .setMessage("视频播放加载错误")
                            .setIcon(android.R.drawable.ic_dialog_info)

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            }).create();
                    dialog.show();

                }
//                return false;//弹框无法播放此视频
                return true; //隐藏弹框
            }
        });

    }

    //检查权限
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        } else {
            initMediaPlayer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "拒绝权限，将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            default:
                break;

        }
    }


    @OnClick({R.id.start, R.id.pause, R.id.resume, R.id.down})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
                break;
            case R.id.pause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                break;
            case R.id.resume:
                if (videoView.isPlaying()) {
                    videoView.resume();
                }
                break;
            case R.id.down:
                startActivity( new Intent(this,DownLoadFilesActivity.class));

                break;

            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
            videoView = null;
        }
    }
}