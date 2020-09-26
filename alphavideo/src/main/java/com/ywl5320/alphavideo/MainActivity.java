package com.ywl5320.alphavideo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlCodecType;
import com.ywl5320.wlmedia.enums.WlComplete;
import com.ywl5320.wlmedia.enums.WlSourceType;
import com.ywl5320.wlmedia.enums.WlVideoTransType;
import com.ywl5320.wlmedia.listener.WlOnCompleteListener;
import com.ywl5320.wlmedia.listener.WlOnPreparedListener;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.surface.WlSurfaceView;
import com.ywl5320.wlmedia.surface.WlTextureView;

public class MainActivity extends AppCompatActivity {

    private WlMedia wlMedia;
    private WlSurfaceView wlSurfaceView;
    private WlTextureView wlTextureView;
    private RelativeLayout rlbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        setContentView(R.layout.activity_main);

        wlSurfaceView = findViewById(R.id.wlsurfaceview);
        wlTextureView = findViewById(R.id.wltextureView);
        rlbg = findViewById(R.id.rl_bg);
        wlSurfaceView.enableTransBg(true);
        wlTextureView.enableTransBg(true);


        wlMedia = new WlMedia();
        wlTextureView.setWlMedia(wlMedia);
        wlMedia.setSourceType(WlSourceType.NORMAL);
        wlMedia.setCodecType(WlCodecType.CODEC_MEDIACODEC);
        wlMedia.setLoopPlay(false);
        wlMedia.setVideoClearColor(0, 0, 0, 0);
        wlMedia.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMedia.start();
            }
        });


        wlMedia.setOnCompleteListener(new WlOnCompleteListener() {
            @Override
            public void onComplete(WlComplete type) {
                wlSurfaceView.setVisibility(View.INVISIBLE);
                wlTextureView.setVisibility(View.INVISIBLE);
            }
        });

        wlMedia.prepared();
    }

    public void surface_trans(View view) {
        wlMedia.enableTransVideo(WlVideoTransType.VIDEO_TRANS_LEFT_ALPHA);
        rlbg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        wlMedia.setSource("/sdcard/Movies/demo_video.mp4");
        wlSurfaceView.setVisibility(View.VISIBLE);
        wlTextureView.setVisibility(View.INVISIBLE);
        wlSurfaceView.updateMedia(wlMedia);
        wlTextureView.updateMedia(null);
        wlMedia.next();
    }

    public void texture_trans(View view) {
        wlMedia.enableTransVideo(WlVideoTransType.VIDEO_TRANS_RIGHT_ALPHA);
        rlbg.setBackgroundColor(getResources().getColor(R.color.colorBlack));
        wlMedia.setSource("/sdcard/Movies/plane_x264.mp4");
        wlSurfaceView.setVisibility(View.INVISIBLE);
        wlTextureView.setVisibility(View.VISIBLE);
        wlSurfaceView.updateMedia(null);
        wlTextureView.updateMedia(wlMedia);
        wlMedia.next();
    }

    public void surface_normal(View view) {
        wlMedia.enableTransVideo(WlVideoTransType.VIDEO_TRANS_NO_ALPHA);
        rlbg.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        wlMedia.setSource("/sdcard/Movies/demo_video.mp4");
        wlSurfaceView.setVisibility(View.VISIBLE);
        wlTextureView.setVisibility(View.INVISIBLE);
        wlSurfaceView.updateMedia(wlMedia);
        wlTextureView.updateMedia(null);
        wlMedia.next();
    }

    public void texture_normal(View view) {
        wlMedia.enableTransVideo(WlVideoTransType.VIDEO_TRANS_NO_ALPHA);
        rlbg.setBackgroundColor(getResources().getColor(R.color.colorBlack));
        wlMedia.setSource("/sdcard/Movies/plane_x264.mp4");
        wlSurfaceView.setVisibility(View.INVISIBLE);
        wlTextureView.setVisibility(View.VISIBLE);
        wlSurfaceView.updateMedia(null);
        wlTextureView.updateMedia(wlMedia);
        wlMedia.next();

    }
}
