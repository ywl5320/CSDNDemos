package com.ywl5320.getvideopic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ywl5320.wlmedia.WlMediaUtil;
import com.ywl5320.wlmedia.bean.WlVideoImgBean;

public class MainActivity extends AppCompatActivity {

    private ImageView ivImg;
    private ImageView ivImg2;
    private ImageView ivImg3;
    private Button btnAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0x01);
        }

        setContentView(R.layout.activity_main);
        ivImg = findViewById(R.id.iv_img);
        ivImg2 = findViewById(R.id.iv_img2);
        ivImg3 = findViewById(R.id.iv_img3);
        btnAll = findViewById(R.id.btn_all);

        ivImg3.setRotation(90);




    }

    public void getPic(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WlMediaUtil wlMediaUtil = new WlMediaUtil();
                wlMediaUtil.setSource("https://hjapi.51hejia.com/VID20191226144815.mp4");
                int ret = wlMediaUtil.init();
                if(ret == 0)
                {
                    ret = wlMediaUtil.openCodec();
                    if(ret == 0)
                    {
                        WlVideoImgBean wlVideoImgBean = wlMediaUtil.getVideoImg(10, false);
                        if(wlVideoImgBean != null)
                        {
                            Message message = Message.obtain();
                            message.obj = wlVideoImgBean;
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                    }
                }
                wlMediaUtil.release();
            }
        }).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    WlVideoImgBean wlVideoImgBean = (WlVideoImgBean) msg.obj;
                    ivImg.setImageBitmap(wlVideoImgBean.getBitmap());
                    break;
                case 1:
                    WlVideoImgBean wlVideoImgBean2 = (WlVideoImgBean) msg.obj;
                    ivImg2.setImageBitmap(wlVideoImgBean2.getBitmap());
                    break;
                case 2:
                    WlVideoImgBean wlVideoImgBean3 = (WlVideoImgBean) msg.obj;
                    ivImg3.setImageBitmap(wlVideoImgBean3.getBitmap());
                    break;
                case 3:
                    btnAll.setText("逐帧获取图片");
                    break;
            }

        }
    };

    public void getLocalPic(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WlMediaUtil wlMediaUtil = new WlMediaUtil();
                wlMediaUtil.setSource("/storage/sdcard0/Movies/GAI周延-大痒痒-哪吒 (《哪吒之魔童降世》电影主题曲)(蓝光).mp4");
                int ret = wlMediaUtil.init();
                if(ret == 0)
                {
                    ret = wlMediaUtil.openCodec();
                    if(ret == 0)
                    {
                        WlVideoImgBean wlVideoImgBean = wlMediaUtil.getVideoImg(55, false);
                        if(wlVideoImgBean != null)
                        {
                            Message message = Message.obtain();
                            message.obj = wlVideoImgBean;
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
                wlMediaUtil.release();
            }
        }).start();
    }

    boolean start = false;
    public void getAllPic(View view) {
        if(!start)
        {
            btnAll.setText("停止");
            start = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WlMediaUtil wlMediaUtil = new WlMediaUtil();
                    wlMediaUtil.setSource("https://hjapi.51hejia.com/VID20191226144815.mp4");
                    int ret = wlMediaUtil.init();
                    if(ret == 0)
                    {
                        ret = wlMediaUtil.openCodec();
                        if(ret == 0)
                        {
                            while(true)
                            {
                                if(!start)
                                {
                                    break;
                                }
                                WlVideoImgBean wlVideoImgBean = wlMediaUtil.getVideoImg(false);
                                if(wlVideoImgBean != null)
                                {
                                    Message message = Message.obtain();
                                    message.obj = wlVideoImgBean;
                                    message.what = 2;
                                    handler.sendMessage(message);
                                }
                                else{
                                    start = false;
                                    break;
                                }
                                try {
                                    Thread.sleep(5);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    wlMediaUtil.release();
                    handler.sendEmptyMessage(3);
                }
            }).start();
        }
        else{
            start = false;
        }
    }
}